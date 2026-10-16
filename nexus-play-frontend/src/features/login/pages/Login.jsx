import React, { useEffect, useRef } from "react";

import { Link, useNavigate, useSearchParams } from "react-router";

import { Controller, useForm } from "react-hook-form";

import { InputText } from "primereact/inputtext";
import { Password } from "primereact/password";
import { Button } from "primereact/button";
import { Divider } from "primereact/divider";
import { Toast } from "primereact/toast";
import { classNames } from "primereact/utils";

import { useAxios } from "../../../hooks/useAxios";

import { MENSAGENS_ERRO_OAUTH } from "../../../errors/errorsOAuth";

import styles from "./Login.module.css";

export default function Login() {
    const BACKEND_URL = import.meta.env.VITE_SERVICE_URL;

    const toast = useRef(null);

    const { post } = useAxios();
    const navigate = useNavigate();
    const [searchParams] = useSearchParams();

    const codigoErroOAuth = searchParams.get("oauth-error");

    const { control, register, handleSubmit, formState: { errors } } = useForm({
        defaultValues: {
            email: "",
            senha: ""
        }
    });

    useEffect(() => {
        if(codigoErroOAuth) {
            const mensagem = MENSAGENS_ERRO_OAUTH[codigoErroOAuth];

            toast.current.show({
                severity: "warn",
                summary: "ATENÇÃO",
                detail: mensagem,
                sticky: true
            });
        }
    }, [codigoErroOAuth])

    const autenticar = async (formLogin) => {
        try {
            const resposta = await post("/auth/login", formLogin);

            if (resposta.status === 200) {
                navigate("/home", { replace: true });
            }

        } catch (exception) {
            const respostaBackend = exception.data;

            toast.current.show({
                severity: "error",
                summary: "Erro",
                detail: respostaBackend.erro
            });
        }
	};

    return (
        <div className={styles.stage}>
            <Toast ref={toast}/>

            {/* Painel de marca — lado esquerdo */}
            <aside className={styles.brandPanel}>
                <div className={styles.orbGlowOne} aria-hidden="true"/>
                <div className={styles.orbGlowTwo} aria-hidden="true"/>
                <div className={styles.scanline} aria-hidden="true"/>

                <div className={styles.brandContent}>
                    <span className={styles.eyebrow}> ACESSO AO SERVIDOR </span>

                    <h1 className={styles.brandTitle}>
                        NEXUS <span className={styles.brandTitleAccent}> PLAY </span>
                    </h1>

                    <p className={styles.brandTagline}>
                        Sua conta, seu progresso, seu squad. Entre para continuar de onde parou.
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

            {/* Painel de formulário — lado direito */}
            <main className={styles.formPanel}>
                <form onSubmit={handleSubmit(autenticar)} className={styles.card}>
                    <header className={styles.cardHeader}>
                        <h2 className={styles.cardTitle}>
                            Entrar
                        </h2>

                        <p className={styles.cardSubtitle}>
                            Ainda não tem conta? {" "}

                            <Link to="/cadastro-usuario" className={styles.link}>
                                Crie uma agora
                            </Link>
                        </p>
                    </header>

                    <div className={styles.field}>
                        <label htmlFor="email" className={styles.label}>
                            E-mail
                        </label>

                        <InputText
                            {...register("email", { 
                                validate: (valor) => {
                                    if (!valor) {
                                        return "Digite seu e-mail";
                                    }

                                    return valor.includes("@") || "Seu e-mail deve conter '@'"
                                }
                            })}
                            id="email"
                            placeholder="seu-email@exemplo.com"
                            className={`${styles.input} ${errors.email && "border-error"}`}
                        />

                        { errors.email && <span className="error-message"> { errors.email.message } </span>}
                    </div>

                    <div className={styles.field}>
                        <div className={styles.labelRow}>
                            <label htmlFor="senha" className={styles.label}>
                                Senha
                            </label>

                            <Link to="#" id="senha" className={styles.linkSmall}>
                                Esqueci minha senha
                            </Link>
                        </div>

                        <Controller
                            name="senha"
                            control={control}
                            rules={{ required: "O campo Senha é obrigatório"}}
                            render={({ field, fieldState }) => (
                                <React.Fragment>
                                    <Password
                                        {...field}
                                        id={field.name}
                                        inputRef={field.ref}
                                        placeholder="Digite sua senha"
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
                                    
                                    { errors.senha && <span className="error-message"> { errors.senha.message } </span>}
                                </React.Fragment>
                            )}
                        />
                    </div>

                    <Button
                        type="submit"
                        label="Entrar"
                        className={styles.primaryButton}
                    />

                    <Divider align="center" className={styles.divider}>
                        <span className={styles.dividerLabel}> ou continue com </span>
                    </Divider>

                    <Button
                        type="button"
                        className={styles.googleButton}
                        icon="pi pi-google"
                        label="Entrar com Google"
                        onClick={() => {                            
                            window.location.href = `${BACKEND_URL}/oauth2/authorization/google`;
                        }}
                    />

                    <p className={styles.terms}>
                        Ao continuar, você concorda com os {" "}

                        <Link to="#" className={styles.linkSmall}>
                            Termos de Uso
                        </Link> {" "}

                        e a {" "}

                        <Link to="#" className={styles.linkSmall}>
                            Política de Privacidade
                        </Link>
                        .
                    </p>
                </form>
            </main>
        </div>
    );
}