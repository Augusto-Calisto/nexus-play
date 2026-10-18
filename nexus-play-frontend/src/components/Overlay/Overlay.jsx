import styles from "./Overlay.module.css";

export default function Overlay({ isVisivel, mensagem="Carregando..." }) {
    if (!isVisivel) return null;

    return (
        <div className={styles.overlayBackdrop}>
            <div className={styles.overlayContainer}>
                <div className={styles.scanline}></div>
                
                <div className={styles.overlayContent}>
                    <i className={`pi pi-spinner pi-spin ${styles.spinner}`}></i>

                    <span className={styles.message}>
                        { mensagem }
                    </span>
                </div>
            </div>
        </div>
    );
}