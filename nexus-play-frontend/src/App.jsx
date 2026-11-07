import { BrowserRouter, Route, Routes } from "react-router";

import { AuthProvider } from "./context/AuthContext";

import ProtectedRoute from "./security/ProtectedRoute";
import SigninAuthCallback from "./security/SigninAuthCallback";

import CadastroUsuario from "./features/cadastro/pages/CadastroUsuario";
import Login from "./features/login/pages/Login";

import AcessoNegado from "./errors/AcessoNegado";
import PaginaNaoEncontrada from "./errors/PaginaNaoEncontrada";
import ListagemJogos from "./features/jogos/pages/ListagemJogos";

function App() {
  	return (
		<BrowserRouter>
			<AuthProvider>
				<Routes>
					<Route path="/" element={<Login/>}/>

					<Route path="/oauth-callback" element={<SigninAuthCallback/>}/>
					
					<Route element={<ProtectedRoute/>}>
						<Route path="/home" element={<ListagemJogos/>}/>
					</Route>

					<Route path="/cadastro-usuario" element={<CadastroUsuario/>}/>

					<Route path="/negado" element={<AcessoNegado/>}/>

					<Route path="*" element={<PaginaNaoEncontrada/>}/>
				</Routes>
			</AuthProvider>
		</BrowserRouter>
	)
}

export default App;