import React, { useState } from "react";

import ModalInfoJogo from "../ModalInfoJogo/ModalInfoJogo";

import semImagem from "../../../../assets/sem-imagem.png"
import styles from "./CardInfoJogo.module.css";

export default function CardInfoJogo({ jogos }) {
    const [jogoSelecionado, setJogoSelecionado] = useState(null);
    const [mostrarModalInfoJogo, setMostrarModalInfoJogo] = useState(false);

    const abrirModalInfoJogo = (jogo) => {
        setJogoSelecionado(jogo);
        setMostrarModalInfoJogo(true);
    }

    if (!jogos || jogos?.length === 0) {
        return (
            <div id="container-sem-jogos">
                <p className={styles.mensagemSemJogo}>
                    Nenhum jogo encontrado
                </p>
            </div>
        )
    }

    return (
        <React.Fragment>
            <ModalInfoJogo
                key="modal-info-jogo"
                modalVisivel={mostrarModalInfoJogo}
                jogo={jogoSelecionado}
                onModalFechado={() => setMostrarModalInfoJogo(false)}
            />

            <div className={styles.gameGrid} title="Clique para ver mais informações do jogo">
                {jogos.map((jogo) => (
                    <div onClick={() => abrirModalInfoJogo(jogo)} id={jogo.id} key={jogo.id} className={styles.gameCard}>
                        <div className={styles.gameImageContainer}>
                            <img
                                src={jogo.urlImagem || semImagem}
                                alt={jogo.nome}
                                className={styles.gameImage}
                            />
                        </div>
                        
                        <div className={styles.gameInfo}>                                        
                            <h3 className={styles.gameName} title={jogo.nome}>
                                { jogo.nome }
                            </h3>
                        </div>
                    </div>
                ))}
            </div>
        </React.Fragment>
    )
}