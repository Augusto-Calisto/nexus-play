import React, { useState } from "react";

import { Dialog } from "primereact/dialog";
import { Divider } from "primereact/divider";

import jogoSemImagem from "../../../../assets/sem-imagem.png";
import styles from "./ModalInfoJogo.module.css";

export default function ModalInfoJogo({ modalVisivel=false, jogo, onModalFechado }) {
    const [activeTab, setActiveTab] = useState("overview");
    
    if (!modalVisivel || !jogo) return null;

    return (
        <Dialog visible={modalVisivel} onHide={onModalFechado} maximizable>
            <div className={styles.modalContentWrapper}>

                {/* Banner Superior com urlImagem e informações principais */}
                <div className={styles.modalBanner}>
                    <img
                        src={jogo.urlImagem || jogoSemImagem}
                        alt={jogo.nome}
                        className={styles.bannerImage}
                    />

                    <div className={styles.bannerGradient}></div>

                    <button className={styles.closeButton} onClick={onModalFechado} aria-label="Fechar">
                        ✕
                    </button>

                    <div className={styles.bannerHeaderInfo}>
                        <span className={styles.badge}>
                            ID: #{ jogo.id }
                        </span>

                        <h2 className={styles.modalGameTitle}>
                            { jogo.nome }
                        </h2>

                        {jogo.urlWebsite && (
                            <a href={jogo.urlWebsite} target="_blank" rel="noreferrer" className={styles.websiteLink}>
                                🌐 Visitar Website Oficial &rarr;
                            </a>
                        )}
                    </div>
                </div>

                {/* Abas de Navegação */}
                <div className={styles.modalTabs}>
                    <button
                        className={`${styles.tabButton} ${activeTab === "overview" ? styles.tabActive : ""}`}
                        onClick={() => setActiveTab("overview")}
                    >
                        VISÃO GERAL
                    </button>

                    <button
                        className={`${styles.tabButton} ${activeTab === "credits" ? styles.tabActive : ""}`}
                        onClick={() => setActiveTab("credits")}
                    >
                        CRÉDITOS & LOJAS
                    </button>

                    <button
                        className={`${styles.tabButton} ${activeTab === "media" ? styles.tabActive : ""}`}
                        onClick={() => setActiveTab("media")}
                    >
                        SCREENSHOTS
                    </button>
                </div>

                {/* Conteúdo do Modal baseado na Aba Ativa */}
                <div className={styles.modalBody}>
                    {activeTab === "overview" && (
                        <React.Fragment>
                            <div className={styles.field}>
                                <span className={styles.label}>// DESCRIÇÃO (description)</span>

                                <p className={styles.synopsisText}>
                                    { jogo.descricao }
                                </p>
                            </div>

                            <div className={styles.statsGrid}>
                                <div className={styles.statBox} style={{ gridColumn: "span 2" }}>
                                    <span className={styles.statBoxLabel}>
                                        MÉDIA GERAL (rating)
                                    </span>

                                    <span className={styles.statBoxValue}>
                                        ★ { jogo.mediaGeral } / 10
                                    </span>
                                </div>
                                
                                <div className={styles.statBox} style={{ gridColumn: "span 2" }}>
                                    <span className={styles.statBoxLabel}>
                                        TEMPO DE JOGO (playtime)
                                    </span>

                                    <span className={styles.statBoxValue}>
                                        { jogo.tempo }h
                                    </span>
                                </div>
                                
                                <div className={styles.statBox} style={{ gridColumn: "span 2" }}>
                                    <span className={styles.statBoxLabel}>
                                        LANÇAMENTO (released)
                                    </span>

                                    <span className={styles.statBoxValueAccent}>
                                        { jogo.dataLancamento }
                                    </span>
                                </div>
                                
                                <div className={styles.statBox} style={{ gridColumn: "span 2" }}>
                                    <span className={styles.statBoxLabel}>
                                        CONQUISTAS
                                    </span>

                                    <span className={styles.statBoxValue}>
                                        { jogo.totalConquistas }
                                    </span>
                                </div>
                                
                                <div className={styles.statBox} style={{ gridColumn: "span 2" }}>
                                    <span className={styles.statBoxLabel}>
                                        QUANTIDADE AVALIAÇÃO
                                    </span>

                                    <span className={styles.statBoxValue}>
                                        { jogo.quantidadeAvaliacao.toLocaleString() } votos
                                    </span>
                                </div>
                            </div>

                            <div className={styles.field}>
                                <span className={styles.label}>// GÊNEROS (genres) & MODOS (tags)</span>
                                
                                <div className={styles.tagsContainer}>
                                    {jogo.generos?.map((g, idx) => (
                                        <span key={`gen-${idx}`} className={styles.tagBadge}>
                                            Gênero: { g.nome }
                                        </span>
                                    ))}
                                    
                                    {jogo.modos?.map((m, idx) => (
                                        <span key={`mod-${idx}`} className={`${styles.tagBadge} ${styles.tagBadgeSecondary}`}>
                                            Modo: { m.nome }
                                        </span>
                                    ))}
                                </div>
                            </div>

                            <div className={styles.field}>
                                <span className={styles.label}>// PLATAFORMAS (platforms)</span>
                                
                                <div className={styles.tagsContainer}>
                                    {jogo.plataformas?.map((p, idx) => (
                                        <span key={`plat-${idx}`} className={styles.specBadge}>
                                            { p.plataforma.nome }
                                        </span>
                                    ))}
                                </div>
                            </div>
                        </React.Fragment>
                    )}

                    {activeTab === "credits" && (
                        <div className={styles.specsList}>
                            {jogo.desenvolvedores && (
                                <div className={styles.specItem}>
                                    <span className={styles.specLabel}>
                                        DESENVOLVEDORES (developers)
                                    </span>

                                    <span className={styles.specValue}>
                                        { jogo.desenvolvedores.map(d => d.nome).join(", ") }
                                    </span>
                                </div>
                            )}
                            
                            {jogo.publicadores && (
                                <div className={styles.specItem}>
                                    <span className={styles.specLabel}>
                                        PUBLICADORAS (publishers)
                                    </span>

                                    <span className={styles.specValue}>
                                        { jogo.publicadores?.map(pub => pub.nome).join(", ") }
                                    </span>
                                </div>
                            )}

                            {jogo.fornecedoras && (
                                <div className={styles.specItem}>
                                    <span className={styles.specLabel}>
                                        FORNECEDORAS / LOJAS (stores)
                                    </span>
                                    
                                    <span className={styles.specValueSuccess}>
                                        • { jogo.fornecedoras?.map(f => f.loja.nome).join(" • ") }
                                    </span>
                                </div>
                            )}
                        </div>
                    )}

                    {activeTab === "media" && (
                        <div className={styles.field}>
                            <span className={styles.label}>
                                // IMAGENS / SHORT SCREENSHOTS
                            </span>

                            {jogo.imagens?.length > 0 ? (
                                <div className={styles.screenshotsGrid}>
                                    {jogo.imagens?.map((img, idx) => (
                                        <div key={`img-${idx}`} className={styles.screenshotCard}>
                                            <img
                                                src={img.url}
                                                alt={`Screenshot ${idx}`}
                                                className={styles.screenshotImg}
                                                title={`Imagem-${idx}`}
                                            />
                                        </div>
                                    ))}
                                </div>
                            ) : (
                                <p> Sem imagens </p>
                            )}
                        </div>
                    )}

                    <Divider className={styles.divider}/>

                    {/* Ações do Rodapé */}
                    <div className={styles.modalFooterActions}>
                        <button className={styles.secondaryButton} onClick={onModalFechado}>
                            FECHAR
                        </button>
                    </div>
                </div>
            </div>
        </Dialog>
    );
}