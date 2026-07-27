// React DOM
import { createRoot } from "react-dom/client";

// Prime React
import { PrimeReactProvider } from "primereact/api";

// Importações do PrimeReact
import "primereact/resources/themes/bootstrap4-light-blue/theme.css";

// PrimeReact core + icons
import "primereact/resources/primereact.min.css";
import "primeicons/primeicons.css";

// Componente Principal
import App from "./App.jsx";

// Estilo Global
import "./index.css";

createRoot(document.getElementById("root")).render(
	<PrimeReactProvider value={{ ripple: true }}>
      	<App/>
    </PrimeReactProvider>
);