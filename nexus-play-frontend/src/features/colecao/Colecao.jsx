import { useEffect, useRef, useState } from "react";

import { Toast } from "primereact/toast";
import { Button } from "primereact/button";
import { ConfirmDialog, confirmDialog } from "primereact/confirmdialog";

import { DataTable } from "primereact/datatable";
import { Column } from "primereact/column";

import NavBar from "../../components/NavBar/NavBar";

import { useAxios } from "../../hooks/useAxios";
import { useAuth } from "../../context/AuthContext";

import ModalColecao from "./components/ModalColecao/ModalColecao";

import styles from "./Colecao.module.css";

export default function Colecao() {
    const { get, del } = useAxios();
    const { user } = useAuth();

    const toast = useRef(null);

    const [colecoes, setColecoes] = useState([]);
    const [totalColecoes, setTotalColecoes] = useState(0);
    const [modalAberto, setModalAberto] = useState(false)
    const [colecaoSelecionada, setColecaoSelecionada] = useState(null);

    const [lazyState, setLazyState] = useState({ first: 0, rows: 10, page: 0 });
    const [mostrarOverlay, setMostrarOverlay] = useState(false);

    useEffect(() => {
        buscarPaginacaoColecoes(lazyState);
    }, [lazyState]);

    const onReceberDadosColecaoPersistida = (mensagem) => {
        setModalAberto(false);

        setColecaoSelecionada(null);

        toast.current.show({
            severity: "success",
            summary: "SUCESSO",
            detail: mensagem,
            sticky: true
        });

        buscarPaginacaoColecoes(lazyState);
    }

    const buscarPaginacaoColecoes = (event) => {        
        setMostrarOverlay(true);

        const pagina = (event.page !== undefined) ? event.page : lazyState.page;
        const quantidade = (event.rows !== undefined) ? event.rows : lazyState.rows;

        get(`/colecao/usuario/${user.idUsuario}?numero-pagina=${pagina}&quantidade-pagina=${quantidade}`)
            .then((response) => {
                if (response.status === 200) {
                    const { colecoes, total } = response.data;

                    setTotalColecoes(total);
                    
                    setColecoes(colecoes);
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

    const abrirModalInformacoesColecao = (colecao) => {
        setColecaoSelecionada(colecao);
        setModalAberto(true);
    }
    
    const excluirColecao = (colecao) => {
        confirmDialog({
            message: `Deseja deletar a coleção ${colecao.nome}?`,

            header: "Confirmation",

            icon: "pi pi-exclamation-triangle",

            accept: () => {
                setMostrarOverlay(true);

                del(`/colecao/excluir/${colecao.id}`)
                    .then((response) => {
                        if (response.status === 200) {
                            const mensagem = response.data;

                            toast.current.show({
                                severity: "success",
                                summary: "SUCESSO",
                                detail: mensagem,
                                sticky: true
                            });

                            buscarPaginacaoColecoes(lazyState);
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
        });
    };

    // Evento disparado quando o usuário muda de página ou altera a quantidade de linhas
    const onPageChange = (event) => {
        setLazyState(event);
    };

    return (
        <div className={styles.stage}>
            <NavBar/>

            <Toast ref={toast}/>

            <ConfirmDialog/>
            
            <ModalColecao
                colecao={colecaoSelecionada}

                modalVisivel={modalAberto}

                onError={(mensagemErro) => {
                    toast.current.show({
                        severity: "error",
                        summary: "ERRO",
                        detail: mensagemErro,
                        sticky: true
                    });
                }}

                onColecaoPersistida={(mensagem) => onReceberDadosColecaoPersistida(mensagem)}

                onModalFechado={() => {
                    setColecaoSelecionada(null);
                    setModalAberto(false);
                }}
            />

            <div className={styles.containerBotaoCadastrarColecao}>
                <Button onClick={() => setModalAberto(true)} title="Criar nova coleção">
                    <i className="pi pi-plus"></i>
                </Button>
            </div>

            <div id="container-root-cards-paginacao" className={styles.formPanel}>
                <div id="container-cards-colecos">
                    <div className={styles.tableCard}>
                        <DataTable
                            value={colecoes}
                            lazy
                            first={lazyState.first} 
                            rows={lazyState.rows} 
                            totalRecords={totalColecoes} 
                            onPage={onPageChange}
                            loading={mostrarOverlay}
                            sortField="nome"
                            paginator
                            paginatorClassName={styles.paginatorCustom}
                            rowsPerPageOptions={[2, 5, 10]}
                            emptyMessage="Nenhuma coleção encontrada"
                        >
                            <Column 
                                field="nome"
                                header="Coleção"
                                sortable
                                style={{ width: "40%" }} 
                            />
        
                            <Column 
                                field="descricao" 
                                header="Descrição" 
                                body={(rowData) => rowData.descricao || "-"} 
                                style={{ width: "30%" }} 
                            />
        
                            <Column 
                                header="Ações" 
                                body={(rowData) => (
                                    <div style={{ display: "flex", gap: "0.5rem" }}>
                                        <Button 
                                            severity="warning"
                                            size="small" 
                                            onClick={() => abrirModalInformacoesColecao(rowData)} 
                                        >
                                            <i className="pi pi-pencil"></i> Alterar
                                        </Button>
                                        
                                        <Button 
                                            severity="danger" 
                                            size="small" 
                                            onClick={() => excluirColecao(rowData)} 
                                        >
                                            <i className="pi pi-trash"></i> Deletar
                                        </Button>
                                    </div>
                                )} 
                                style={{ width: "20%" }} 
                            />
                        </DataTable>
                    </div>
                </div>
            </div>
        </div>
    );
}