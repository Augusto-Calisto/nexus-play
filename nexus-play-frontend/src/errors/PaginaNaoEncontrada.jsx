import { Link } from "react-router";

import { Button } from "primereact/button";

import styles from "./PaginaNaoEncontrada.module.css";

export default function PaginaNaoEncontrada() {
    return (
        <div className={styles.stage}>

            {/* Painel de marca — lado esquerdo (mantém a identidade visual) */}
            <aside className={styles.brandPanel}>
                <div className={styles.orbGlowOne} aria-hidden="true"/>
                <div className={styles.orbGlowTwo} aria-hidden="true"/>
                <div className={styles.scanline} aria-hidden="true"/>

                <div className={styles.brandContent}>
                    <span className={styles.eyebrow}> ERRO 404 </span>

                    <h1 className={styles.brandTitle}>
                        NEXUS <span className={styles.brandTitleAccent}> PLAY </span>
                    </h1>

                    <p className={styles.brandTagline}>
                        Parece que você se perdeu no ciberespaço ou o servidor que você procurava foi abduzido.
                    </p>

                    <ul className={styles.statList}>
                        <li>
                            <span className={styles.statValue}>404</span>
                            <span className={styles.statLabel}>página não encontrada</span>
                        </li>

                        <li>
                            <span className={styles.statValue}>01</span>
                            <span className={styles.statLabel}>direção errada</span>
                        </li>
                    </ul>
                </div>
            </aside>

            {/* Painel de erro / ação — lado direito */}
            <main className={styles.formPanel}>
                <div className={styles.card}>
                    <header className={styles.cardHeader}>
                        <span className={styles.errorCode}>404</span>
                        
                        <h2 className={styles.cardTitle}>
                            Zona Desconhecida
                        </h2>

                        <p className={styles.cardSubtitle}>
                            A rota que você tentou acessar não existe ou foi movida para outro setor da rede.
                        </p>
                    </header>

                    <div className={styles.actionContainer}>
                        <Link to="/" style={{ textDecoration: 'none', width: '100%' }}>
                            <Button
                                type="button"
                                label="Retornar ao Início"
                                className={styles.primaryButton}
                            />
                        </Link>

                        <div className={styles.helpText}>
                            Precisa de ajuda? {" "}
                            
                            <Link to="#" className={styles.link}>
                                Contate o suporte
                            </Link>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    );
}