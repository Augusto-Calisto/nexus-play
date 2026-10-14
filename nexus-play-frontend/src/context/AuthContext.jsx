import { createContext, useContext, useEffect, useState } from "react";

import { useLocation, useNavigate } from "react-router";

import { useAxios } from "../hooks/useAxios";

const AuthContext = createContext({
    isAuthenticated: false,
    loading: false,
    user: null,
    logout: () => {}
})

export const useAuth = () => useContext(AuthContext);

export const AuthProvider = ({ children }) => {
    const BACKEND_URL = import.meta.env.VITE_SERVICE_URL;

    const { get } = useAxios();
    const location = useLocation();
    const navigate = useNavigate();

    const [loading, setLoading] = useState(true);
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [user, setUser] = useState(null);

    useEffect(() => {
        if (!isAuthenticated) {
            checkAuth();

        } else if (isAuthenticated && location.pathname === "/") {
            navigate("/home");
        }
    }, [isAuthenticated, navigate]);

    const checkAuth = async () => {
        try {
            const response = await get("/usuario/info");
            
            if (response.status === 200) {
                setUser(response.data);

                setIsAuthenticated(true);

                return response.data;
            }

            setUser(null);

            setIsAuthenticated(false);

        } catch (e) {            
            setUser(null);

            setIsAuthenticated(false);

        } finally {
            setLoading(false);
        }
    }

    const logout = () => {
        setIsAuthenticated(false);
        setUser(null);
        window.location.href = `${BACKEND_URL}/logout`;
    };

    return (
        <AuthContext.Provider value={{ isAuthenticated, loading, user, logout }}>
            { children }
        </AuthContext.Provider>
    )
}