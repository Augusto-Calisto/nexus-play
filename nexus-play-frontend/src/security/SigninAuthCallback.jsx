import { useEffect } from "react";

import { useNavigate } from "react-router";

import { useAuth } from "../context/AuthContext";

export default function SigninAuthCallback() {
	const { user, isAuthenticated } = useAuth();
	const navigate = useNavigate();

	useEffect(() => {
		if(user && isAuthenticated) {
			navigate("/home", { replace: true });
		} else {
			navigate("/", { replace: true });
		}
	}, []);

	return <p> Redirecionando, aguarde... </p>;
}