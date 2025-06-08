// frontend_app/src/common/UpcomingEventsSection.jsx
import React, { useState, useEffect } from 'react';
import ApiService from '../../../service/ApiService';
import styles from './KommendeVeranstalltungen.module.css';
import { FaMapMarkerAlt, FaCalendarAlt } from 'react-icons/fa';

const UpcomingEventsSection = () => {
    const [events, setEvents] = useState([]);

    useEffect(() => {
        // Tüm etkinlikleri getAllEvents kullanarak çek
        ApiService.getAllEvents()
            .then(data => {
                // API farklı formatlarda dönebilir; array veya objenin eventList'i
                const list = Array.isArray(data)
                    ? data
                    : Array.isArray(data.eventList)
                        ? data.eventList
                        : [];
                setEvents(list);
            })
            .catch(err => console.error(err));
    }, []);

    return (
        <section className={styles.container}>
            <h2 className={styles.title}>Kommende Veranstaltungen</h2>
            <div className={styles.grid}>
                {events.map(evt => (
                    <div key={evt.id} className={styles.card}>
                        <div className={styles.imageWrapper}>
                            <img src={evt.imageUrl || evt.eventPhotoUrl || ''} alt={evt.title || evt.eventName} className={styles.image} />
                            <div className={styles.overlay}>
                                <p className={styles.description}>{evt.description || evt.eventDescription}</p>
                                <div className={styles.meta}>
                                    <span><FaCalendarAlt /> {evt.eventDate || evt.date}</span>
                                    <span><FaMapMarkerAlt /> {(evt.eventLocation && evt.eventLocation.city) || evt.location}</span>
                                </div>
                                <button className={styles.button}>Mehr erfahren →</button>
                            </div>
                        </div>
                        <div className={styles.info}>
                            <h3 className={styles.eventTitle}>{evt.eventName || evt.title}</h3>
                        </div>
                    </div>
                ))}
            </div>
        </section>
    );
};

export default UpcomingEventsSection;