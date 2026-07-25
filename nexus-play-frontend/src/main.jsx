// React DOM
import { createRoot } from "react-dom/client";

// Componente Principal
import App from "./App.jsx";

// Estilo Global
import "./index.css";

createRoot(document.getElementById("root")).render(
	<App/>
);