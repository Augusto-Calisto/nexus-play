import { Link } from "react-router";

import { Button } from "primereact/button";

import styles from "./AcessoNegado.module.css";

export default function AcessoNegado() {
    return (
        <div className={styles.stage}>

            {/* Painel de marca — lado esquerdo (mantendo o mesmo padrão visual) */}
            <aside className={styles.brandPanel}>
                <div className={styles.orbGlowOne} aria-hidden="true"/>
                <div className={styles.orbGlowTwo} aria-hidden="true"/>
                <div className={styles.scanline} aria-hidden="true"/>

                <div className={styles.brandContent}>
                    <span className={styles.eyebrow}> SEGURANÇA DA PLATAFORMA </span>

                    <h1 className={styles.brandTitle}>
                        NEXUS <span className={styles.brandTitleAccent}> PLAY </span>
                    </h1>

                    <p className={styles.brandTagline}>
                        Área restrita. Suas credenciais atuais não possuem autorização para acessar este setor.
                    </p>

                    <ul className={styles.statList}>
                        <li>
                            <span className={styles.statValue}>403</span>
                            <span className={styles.statLabel}>código de erro</span>
                        </li>

                        <li>
                            <span className={styles.statValue}>SEC-9</span>
                            <span className={styles.statLabel}>protocolo de defesa</span>
                        </li>
                    </ul>
                </div>
            </aside>

            {/* Painel de aviso de Acesso Negado — lado direito */}
            <main className={styles.formPanel}>
                <div className={styles.card}>
                    <header className={styles.cardHeader}>
                        <div className={styles.errorCodeBadge}>
                            <span>ERRO 403</span>
                        </div>

                        <h2 className={styles.cardTitle}>
                            Acesso Negado
                        </h2>

                        <p className={styles.cardSubtitle}>
                            Você não tem permissão para visualizar esta página. Verifique suas credenciais ou retorne para o início.
                        </p>
                    </header>

                    {/* Alerta visual de segurança */}
                    <div className={styles.warningBox}>
                        <i className="pi pi-shield-slash" style={{ fontSize: '1.5rem', color: 'var(--accent-danger)' }}></i>
                        <div>
                            <span className={styles.warningTitle}>Zona Restrita</span>
                            <p className={styles.warningText}>Esta tentativa de acesso foi registrada por motivos de segurança.</p>
                        </div>
                    </div>

                    <div className={styles.buttonGroup}>
                        <Link to="/" style={{ textDecoration: 'none', width: '100%' }}>
                            <Button
                                type="button"
                                label="Voltar ao Início"
                                className={styles.primaryButton}
                            />
                        </Link>

                        <Link to="/" className={styles.linkSmallCenter}>
                            Fazer login com outra conta
                        </Link>
                    </div>
                </div>
            </main>
        </div>
    );
}