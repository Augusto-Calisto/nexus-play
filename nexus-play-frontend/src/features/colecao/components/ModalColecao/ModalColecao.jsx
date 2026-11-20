import { useEffect } from "react";

import { Dialog } from "primereact/dialog";
import { InputText } from "primereact/inputtext";
import { Button } from "primereact/button";
import { InputTextarea } from "primereact/inputtextarea";

import { useForm, Controller } from "react-hook-form";

import { useAxios } from "../../../../hooks/useAxios";
import { useAuth } from "../../../../context/AuthContext";

import styles from "./ModalColecao.module.css";

export default function ModalColecao({ modalVisivel=false, colecao=null, onModalFechado, onColecaoPersistida, onError }) {
    const { user } = useAuth();
    const { post, put } = useAxios();

    const { control, handleSubmit, reset, setValue, formState: { errors } } = useForm({
        defaultValues: {
            idColecao: 0,
            nome: "",
            descricao: "",
            idUsuario: user.idUsuario
        }
    });

    useEffect(() => {        
        if (colecao) {
            setValue("idColecao", colecao.id);

            setValue("nome", colecao.nome);

            const valorDescricao = colecao.descricao ?? "";

            setValue("descricao", valorDescricao);

            setValue("idUsuario", user.idUsuario);
        }
    }, [colecao]);

    const persistirColecao = (dados) => {        
        const valorIdColecao = dados["idColecao"];
        
        if (valorIdColecao === 0) {
            post("/colecao/criar", dados)
                .then((response) => {
                    if (response.status === 201) {
                        reset();
                        onColecaoPersistida(response.data);
                    }
                })
                .catch((exception) => {                
                    const { erro } = exception.data;
                    onError(erro);
                })

        } else {
            put("/colecao/alterar", dados)
                .then((response) => {                    
                    if (response.status === 200) {
                        reset();
                        onColecaoPersistida(response.data);
                    }
                })
                .catch((exception) => {                
                    const { erro } = exception.data;
                    onError(erro);
                })
        }
    };

    const fecharModal = () => {
        reset();
        onModalFechado();
    }

    return (
        <Dialog visible={modalVisivel} onHide={fecharModal}>
            <form onSubmit={handleSubmit(persistirColecao)}>
                <div id="container-titulo-modal">
                    <h2> { colecao ? "Alteração" : "Cadastro" } </h2>
                </div>

                <div>
                    <label htmlFor="nome">
                        Nome
                    </label>

                    <Controller
                        name="nome"
                        control={control}
                        rules={{ required: "O nome é obrigatório" }}
                        render={({ field }) => (
                            <div>
                                <InputText 
                                    id="nome" 
                                    {...field} 
                                    placeholder="Nome da coleção" 
                                    className={`${styles.inputNome} ${errors.nome && "border-error"}`}
                                />
                            </div>
                        )}
                    />

                    { errors.nome && <span className="error-message"> { errors.nome.message } </span> }
                </div>

                <div className={styles.containerDescricao}>
                    <label htmlFor="descricao">
                        Descrição
                    </label>
                    
                    <Controller
                        name="descricao"
                        control={control}
                        render={({ field }) => (
                            <div>
                                <InputTextarea
                                    id="descricao" 
                                    {...field}
                                    rows={3}
                                    placeholder="Breve descrição da coleção..." 
                                    className={styles.inputTextArea}
                                />
                            </div>
                        )}
                    />
                </div>

                <div style={{ marginTop: "0.5rem" }}>
                    <Button
                        type="submit"
                        severity={colecao && "warning"}
                        label={colecao ? "Alterar" : "Cadastrar"}
                    />

                    <Button
                        type="button"
                        label="Fechar"
                        severity="secondary"
                        onClick={fecharModal}
                        style={{ flex: 1, marginLeft: 10 }}
                        title="Clique ou pressione ESC"
                    />
                </div>
            </form>
        </Dialog>
    );
}