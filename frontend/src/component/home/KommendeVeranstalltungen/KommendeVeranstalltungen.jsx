// frontend_app/src/common/UpcomingEventsSection.jsx
import React, { useState, useEffect } from 'react';
import ApiService from '../../../service/ApiService';
import styles from './KommendeVeranstalltungen.module.css';
import { FaMapMarkerAlt, FaCalendarAlt } from 'react-icons/fa';
import EventCard from '../../common/EcentCard/EventCard';

const UpcomingEventsSection = () => {
    const [events, setEvents] = useState([]);

    useEffect(() => {
        // Öncelikle tüm etkinlikleri çek
        ApiService.getAllEvents()
            .then(data => {
                const list = Array.isArray(data)
                    ? data
                    : Array.isArray(data.eventList)
                        ? data.eventList
                        : [];

                // Bugünün tarih string’i (YYYY-MM-DD)
                const today = new Date().toISOString().slice(0, 10);

                // Bugüne denk düşen etkinlikleri filtrele ve en fazla 10 tanesini al
                const todaysEvents = list
                    .filter(evt => {
                        const evtDate = (evt.eventDate || evt.date || '').slice(0, 10);
                        return evtDate === today;
                    })
                    .slice(0, 10);

                setEvents(todaysEvents);
            })
            .catch(err => console.error(err));
    }, []);

    return (
        <section className={styles.container}>
            <div className={styles.titleBlock}>
                <h2 className={styles.title}>Kommende Veranstaltungen</h2>
            </div>
            {events.length === 0 ? (
                <p className={styles.noEvents}>Heute keine Veranstaltungen.</p>
            ) : (
                <div className={styles.grid}>
                    {events.map(evt => (
                        <EventCard key={evt.id} event={evt} />
                    ))}
                </div>
            )}
        </section>
    );
};

export default UpcomingEventsSection;
