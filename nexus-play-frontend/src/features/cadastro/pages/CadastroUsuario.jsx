import React, { useRef, useState } from "react";

import { Link } from "react-router";

import { Controller, useForm } from "react-hook-form";

import { InputText } from "primereact/inputtext";
import { Password } from "primereact/password";
import { Button } from "primereact/button";
import { Toast } from "primereact/toast";
import { classNames } from "primereact/utils";

import Overlay from "../../../components/Overlay/Overlay";

import { useAxios } from "../../../hooks/useAxios";

import styles from "./CadastroUsuario.module.css";

export default function CadastroUsuario() {
    const { post } = useAxios();

    const toast = useRef(null);

    const [mostrarOverlay, setMostrarOverlay] = useState(false);
    
    const { control, register, handleSubmit, reset, formState: { errors } } = useForm({
        defaultValues: {
            nome: "",
            username: "",
            email: "",
            senha: ""
        }
    });

    const cadastrarUsuario = async (formCadastro) => {
        try {
            setMostrarOverlay(true);

            const resposta = await post("/usuario/cadastrar", formCadastro);

            if (resposta.status === 201) {
                setMostrarOverlay(false);

                reset();

                toast.current.show({
                    severity: "success",
                    summary: "SUCESSO",
                    detail: "Conta criada com sucesso. Volte para tela de login"
                });
            }
        } catch (exception) {
            const respostaBackend = exception.data;

            toast.current.show({
                severity: "error",
                summary: "ERRO",
                detail: respostaBackend.erro,
                sticky: true
            });
            
        } finally {
            setMostrarOverlay(false);
        }
    };

    return (
        <div className={styles.stage}>
            <Toast ref={toast}/>

            <Overlay isVisivel={mostrarOverlay} mensagem="Validando dados..."/>

            {/* Painel de marca — lado esquerdo */}
            <aside className={styles.brandPanel}>
                <div className={styles.orbGlowOne} aria-hidden="true"/>
                <div className={styles.orbGlowTwo} aria-hidden="true"/>
                <div className={styles.scanline} aria-hidden="true"/>

                <div className={styles.brandContent}>
                    <span className={styles.eyebrow}> NOVO RECRUTA </span>

                    <h1 className={styles.brandTitle}>
                        NEXUS <span className={styles.brandTitleAccent}> PLAY </span>
                    </h1>

                    <p className={styles.brandTagline}>
                        Crie sua conta, entre para a comunidade e domine os rankings da plataforma.
                    </p>

                    <ul className={styles.statList}>
                        <li>
                            <span className={styles.statValue}>2.4M</span>
                            <span className={styles.statLabel}>jogadores ativos</span>
                        </li>

                        <li>
                            <span className={styles.statValue}>128</span>
                            <span className={styles.statLabel}>servidores online</span>
                        </li>
                    </ul>
                </div>
            </aside>

            <main className={styles.formPanel}>
                <form onSubmit={handleSubmit(cadastrarUsuario)} className={styles.card}>
                    <header className={styles.cardHeader}>
                        <h2 className={styles.cardTitle}>
                            Criar Conta
                        </h2>

                        <p className={styles.cardSubtitle}>
                            Já tem uma conta? {" "}

                            <Link to="/" className={styles.link}>
                                Entre agora
                            </Link>
                        </p>
                    </header>

                    {/* Campo Nome */}
                    <div className={styles.field}>
                        <label htmlFor="nome" className={styles.label}>
                            Nome completo
                        </label>

                        <InputText
                            id="nome"
                            {...register("nome", { 
                                required: "O campo Nome é obrigatório",
                                minLength: {
                                    value: 3,
                                    message: "Nome precisar ter pelo menos 3 caracteres"
                                } 
                            })}
                            placeholder="Seu nome completo"
                            className={`${styles.input} ${errors.nome && "border-error"}`}
                        />

                        { errors.nome && <span className="error-message"> { errors.nome.message } </span>}
                    </div>

                    <div className={styles.field}>
                        <label htmlFor="username" className={styles.label}>
                            Gamertag / Username
                        </label>

                        <InputText
                            id="username"
                            {...register("username", { 
                                required: "O campo Username é obrigatório",
                                minLength: {
                                    value: 6,
                                    message: "A Username deve ter pelo menos 6 caracteres"
                                }
                            })}
                            placeholder="Ex: ShadowSniper99"
                            className={`${styles.input} ${errors.username && "border-error"}`}
                        />

                        { errors.username && <span className="error-message"> { errors.username.message } </span> }
                    </div>

                    {/* Campo E-mail */}
                    <div className={styles.field}>
                        <label htmlFor="email" className={styles.label}>
                            E-mail
                        </label>

                        <InputText
                            id="email"
                            {...register("email", { 
                                required: "O campo E-mail é obrigatório",
                                pattern: {
                                    value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i,
                                    message: "Endereço de e-mail inválido"
                                }
                            })}
                            placeholder="seu-email@exemplo.com"
                            className={`${styles.input} ${errors.email && "border-error"}`}
                        />

                        { errors.email && <span className="error-message"> { errors.email.message } </span> }
                    </div>

                    {/* Campo Senha */}
                    <div className={styles.field}>
                        <label htmlFor="senha" className={styles.label}>
                            Senha
                        </label>

                        <Controller
                            name="senha"
                            control={control}
                            rules={{ 
                                required: "O campo Senha é obrigatório",
                                minLength: {
                                    value: 8,
                                    message: "A senha deve ter pelo menos 8 caracteres"
                                }
                            }}
                            render={({ field, fieldState }) => (
                                <React.Fragment>
                                    <Password
                                        {...field}
                                        id={field.name}
                                        inputRef={field.ref}
                                        placeholder="Crie uma senha forte"
                                        toggleMask
                                        className={classNames({ "border-error" : fieldState.error })}
                                        inputClassName={styles.passwordInput}
                                        feedback={false}
                                        pt={{
                                            root: { style: { width: "100%" } },
                                            input: { style: { width: "100%" } },
                                            iconField: { root: { style: { width: "100%" } } }
                                        }}
                                    />
                                    
                                    { errors.senha && <span className="error-message"> { errors.senha.message } </span> }
                                </React.Fragment>
                            )}
                        />
                    </div>

                    <Button
                        id="btn-enviar-form"
                        type="submit"
                        label={"Cadastrar"}
                        className={styles.primaryButton}
                    />
                </form>
            </main>
        </div>
    );
}