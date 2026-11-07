import { useRef } from "react";

import { Link } from "react-router";

import { Menu } from "primereact/menu";

import { useAuth } from "../../context/AuthContext";

import styles from "./NavBar.module.css";

export default function NavBar() {
    const { user, logout } = useAuth();
    
    const { nome, username } = user;

    const menuRight = useRef(null);

    const opcoesMenu = [
        {
            template: (options) => {
                return (
                    <div className={styles.menuContainer}>
                        <ul className={styles.menuList}>
                            <li className={styles.menuItem}>
                                <i className="pi pi-fw pi-user"></i> Perfil
                            </li>

                            <li className={styles.menuItem}>
                                <i className="pi pi-fw pi-cog"></i> Configurações
                            </li>
                        </ul>

                        <hr className={styles.menuDivider}/>

                        <button className={styles.logoutButton} onClick={logout} title="Sair da sua sessão">
                            <i className="pi pi-fw pi-sign-out"></i> Sair
                        </button>
                    </div>
                );
            }
        }
    ];

    const getIniciais = (nomeStr) => {
        return nomeStr
            .split(" ")
            .map((n) => n[0])
            .join("")
            .toUpperCase()
            .substring(0, 2);
    };

    return (
        <header className={styles.navbar}>
            <Link to="/home" className={styles.brand}>
                <div className={styles.brandIcon}>⚡</div>

                <h1 className={styles.brandLogo}>
                    NEXUS <span className={styles.brandAccent}> PLAY </span>
                </h1>
            </Link>

            {/* Centro: Links de Navegação */}
            <ul className={styles.navLinks}>
                <li>
                    <a href="#inicio" className={`${styles.navLink} ${styles.navLinkActive}`}>
                        Início
                    </a>
                </li>

                <li>
                    <a href="#jogos" className={styles.navLink}>
                        Coleções
                    </a>
                </li>

                <li>
                    <a href="#comunidade" className={styles.navLink}>
                        Comunidade
                    </a>
                </li>
            </ul>

            {/* Direita: Notificações e Perfil de Usuário em Pílula */}
            <div className={styles.rightSection}>
                <button className={styles.iconButton} aria-label="Notificações">
                    🔔 <span className={styles.notificationBadge}> 10 </span>
                </button>

                {/* Perfil do Usuário */}
                <div className={styles.userProfile} onClick={(event) => menuRight.current.toggle(event)}>
                    <div className={styles.userAvatar}>
                        { getIniciais(nome) }

                        <Menu model={opcoesMenu} ref={menuRight} popupAlignment="right" popup/>
                    </div>

                    <div className={styles.userDetails}>
                        <span className={styles.userName}>
                            { username }
                        </span>
                    </div>
                </div>
            </div>
        </header>
    );
}