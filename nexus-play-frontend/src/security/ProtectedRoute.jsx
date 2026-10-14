// React Router Dom
import { Navigate, Outlet } from "react-router";

// Auth Context
import { useAuth } from "../context/AuthContext";

const ProtectedRoute = ({ allowedRoles }) => {
    const { user, isAuthenticated, loading } = useAuth();

    if(loading) {
        return <div> Carregando... </div>;
    }

    // 1. Verifica se o usuário não está logado
    if (!user || !isAuthenticated) {
        return <Navigate to="/" replace/>;
    }

    // 2. Caso não passe regra de segurança será acessivel
    if(!allowedRoles || allowedRoles.length === 0) {
        return <Outlet/>;
    }

    // 3. Verifica se a role do usuário está na lista de permissões
    const userRoles = user?.roles;

    let hasAccess = false;
    
    userRoles.forEach((role) => {
        if (allowedRoles.includes(role)) {
            hasAccess = true;
            return ;
        }
    });

    return hasAccess ? <Outlet/> : <Navigate to="/negado" replace/>;
};

export default ProtectedRoute;