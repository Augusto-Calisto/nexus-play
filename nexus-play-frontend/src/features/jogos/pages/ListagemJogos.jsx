import { useEffect, useRef, useState } from "react";

import { Paginator } from "primereact/paginator";
import { Toast } from "primereact/toast";
import { Button } from "primereact/button";
import { InputText } from "primereact/inputtext";

import { useAxios } from "../../../hooks/useAxios";

import NavBar from "../../../components/NavBar/NavBar";
import Overlay from "../../../components/Overlay/Overlay";

import CardInfoJogo from "../components/CardInfoJogo/CardInfoJogo";

import styles from "./ListagemJogos.module.css";

export default function ListagemJogos() {
    const { get } = useAxios();

    const [jogos, setJogos] = useState([]);

    const [totalJogos, setTotalJogos] = useState(0);
    const [totalPaginas, setTotalPaginas] = useState(0);
    const [numeroAtualPagina, setNumeroAtualPagina] = useState(1);
    const [indicePaginacao, setIndicePaginacao] = useState(0);
    const [quantidadeJogosPorPagina, setQuantidadeJogosPorPagina] = useState(20);

    const [nomeJogoPesquisa, setNomeJogoPesquisa] = useState("");
    const [modoPesquisa, setModoPesquisa] = useState(false);

    const [mostrarOverlay, setMostrarOverlay] = useState(false);

    const toast = useRef(null);    

// --------------------------------------- Effect's --------------------------------------------
    useEffect(() => {
        buscarPaginacaoJogos(numeroAtualPagina, quantidadeJogosPorPagina);
    }, []);


// ---------------------------------------- Funções --------------------------------------------    
    const buscarPaginacaoJogos = (pagina, quantidade) => {
        setMostrarOverlay(true);

        get(`/jogos?numero-pagina=${pagina}&quantidade-pagina=${quantidade}`)
            .then((response) => {
                if (response.status === 200) {
                    const jogosPaginacao = response.data;

                    let { total, jogos, numeroPagina, quantidadePagina, totalPaginas } = jogosPaginacao;

                    setTotalJogos(total);

                    setJogos(jogos);

                    setNumeroAtualPagina(numeroPagina);

                    setQuantidadeJogosPorPagina(quantidadePagina);

                    setTotalPaginas(totalPaginas);

                    numeroPagina--;

                    setIndicePaginacao(numeroPagina * quantidadePagina);
                }
            })
            .catch((exception) => {
                const { erro } = exception.data;

                toast.current.show({
                    severity: "error",
                    summary: "ERRO",
                    detail: erro,
                    sticky: true
                });
            })
            .finally(() => {
                setMostrarOverlay(false);
            })
    }

    const pesquisarJogo = (nomeJogo, numeroPagina=1, quantidadePagina=20) => {
        setModoPesquisa(true);

        setMostrarOverlay(true);

        get(`/jogos/nome?jogo=${nomeJogo}&numero-pagina=${numeroPagina}&quantidade-pagina=${quantidadePagina}`)
            .then((response) => {
                if (response.status === 200) {                    
                    const jogosPaginacao = response.data;

                    let { total, jogos, numeroPagina, quantidadePagina, totalPaginas } = jogosPaginacao;

                    setTotalJogos(total);

                    setJogos(jogos);

                    setNumeroAtualPagina(numeroPagina);

                    setQuantidadeJogosPorPagina(quantidadePagina);

                    setTotalPaginas(totalPaginas);

                    numeroPagina--;

                    setIndicePaginacao(numeroPagina * quantidadePagina);
                }
            })
            .catch((exception) => {
                setNomeJogoPesquisa("");

                setModoPesquisa(false);

                const { erro } = exception.data;

                toast.current.show({
                    severity: "error",
                    summary: "ERRO",
                    detail: erro,
                    sticky: true
                });
            })
            .finally(() => {
                setMostrarOverlay(false);
            })
    }

    const sairModoPesquisa = () => {
        setNomeJogoPesquisa("");
        
        setModoPesquisa(false);

        buscarPaginacaoJogos(1, 20);
    }

    const mudancaPagina = (event) => {
        window.scroll({
            top: 0,
            left: 0,
            behavior: "smooth"
        });
        
        const numeroProximaPagina = event.page + 1;

        const quantidadeJogosPorPagina = event.rows;

        if(modoPesquisa) {
            pesquisarJogo(nomeJogoPesquisa, numeroProximaPagina, quantidadeJogosPorPagina);

        } else {
            buscarPaginacaoJogos(numeroProximaPagina, quantidadeJogosPorPagina);
        }
    }


// ----------------------------------------- Renderização -----------------------------------------------------
    return (
        <div className={styles.stage}>
            <NavBar/>

            <Toast ref={toast}/>

            <Overlay isVisivel={mostrarOverlay}/>

            <div id="container-root-form" className={styles.brandContent}>
                <div id="sub-container-form" style={{ padding: "2rem 2rem" }}>
                    <form id="form-pesquisa-jogo" className={styles.searchWrapper}
                        onSubmit={(e) => {
                            e.preventDefault();
                            pesquisarJogo(nomeJogoPesquisa);
                        }} 
                    >
                        <div className={styles.searchInputContainer}>
                            <i className={`pi pi-search ${styles.searchIcon}`}></i>
            
                            <InputText
                                id="nome-jogo"
                                value={nomeJogoPesquisa}
                                onChange={(e) => setNomeJogoPesquisa(e.target.value)}
                                placeholder="Pesquisar jogo pelo nome..."
                                required
                            />
                        </div>
            
                        <Button type="submit" size="small" className={styles.searchButton}>
                            <i className="pi pi-search"></i> Pesquisar
                        </Button>

                        {modoPesquisa && (
                            <Button type="button" size="small" severity="secondary" onClick={sairModoPesquisa}>
                                Voltar para Inicio
                            </Button>
                        )}
                    </form>
                </div>

                <ul className={styles.statList}>
                    <li>
                        <span className={styles.statLabel}>
                            Total de Jogos
                        </span>

                        <span className={styles.statValue}>
                            { new Intl.NumberFormat("pt-BR").format(totalJogos) }
                        </span>
                    </li>
                </ul>
            </div>

            <div id="container-numero-pagina" className={styles.cardHeader} style={{ marginLeft: 35 }}>
                <p className={styles.cardSubtitle}>
                    Página { numeroAtualPagina < totalPaginas ? numeroAtualPagina : totalPaginas } de { totalPaginas }
                </p>
            </div>

            {/* Painel de Listagem e Paginação (Direita) */}
            <div id="container-root-cards-paginacao" className={styles.formPanel}>
                <div id="container-cards-jogos">
                    <CardInfoJogo jogos={jogos}/>
                </div>

                <div id="container-paginacao" className={styles.paginatorWrapper}>
                    <Paginator
                        first={indicePaginacao}
                        rows={quantidadeJogosPorPagina}
                        rowsPerPageOptions={[10, 20, 30]}
                        totalRecords={totalJogos}
                        onPageChange={mudancaPagina}
                    />
                </div>
            </div>
        </div>
    );
}