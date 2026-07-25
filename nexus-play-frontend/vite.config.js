import { defineConfig, loadEnv } from "vite";
import react from "@vitejs/plugin-react";

export default defineConfig(({ mode }) => {

	// Carrega as variáveis de ambiente com base no modo atual (ex: .env.development)
  	// O terceiro parâmetro "" indica que queremos carregar todas as variáveis, 
  	// independentemente do prefixo VITE_ (mas dentro do config)
  	const env = loadEnv(mode, process.cwd(), "");

	return {
		plugins: [react()],

		define: {
			REACT_SERVICE_URL: JSON.stringify(env.VITE_SERVICE_URL),
		},

		server: {
			port: 5173
		}
  	}
});