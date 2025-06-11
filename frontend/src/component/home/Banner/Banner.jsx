// frontend_app/src/components/Banner.jsx
import React from "react";
import styles from "./Banner.module.css";
import rathaus from "../../../assets/images/rathaus_herne.jpg";
import RoomSearch from "../../common/EventSearch/EventSearch";

const Banner = ({ onSearch }) => (
    <section className={styles.banner}>
        <div className={styles.left}>
            <div className={styles.welcome_part}>
                <h1>
                    Willkommen auf der{" "}
                    <span className={styles.highlight}>Herne Event-Plattform</span>
                </h1>
                <p>Entdecke aktuelle Konzerte, Workshops und Sportevents in deiner Stadt.</p>
                <a href="/events" className={styles.cta}>
                    Zu den Veranstaltungen
                </a>
            </div>

        </div>

        <div className={styles.right}>
            <img src={rathaus} alt="Rathaus Herne" className={styles.image} />
        </div>

        {/* Ortayı kesen, yüzen arama bileşeni */}
            <RoomSearch handleSearchResult={onSearch} />
    </section>
);

export default Banner;
