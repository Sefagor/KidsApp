// frontend_app/src/common/EventCard.jsx
import React from 'react';
import { NavLink } from 'react-router-dom';
import styles from './EventCard.module.css';
import { FaCalendarAlt, FaMapMarkerAlt } from 'react-icons/fa';

const EventCard = ({ event }) => {
    const {
        id,
        eventName,
        eventDescription,
        eventDate,
        eventLocation,
        imageUrl,
    } = event;

    const formattedDate = new Date(eventDate).toLocaleDateString('de-DE', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    });

    return (
        <div className={styles.card}>
            <div className={styles.imageWrapper}>
                <img
                    src={"data:image/jpeg;base64," + event.eventPhoto}
                    alt={eventName}
                    className={styles.image}
                />
                <div className={styles.overlay}>
                    <p className={styles.description}>{eventDescription}</p>
                    <div className={styles.meta}>
                        <span><FaCalendarAlt /> {formattedDate}</span>
                        {eventLocation?.city && (
                            <span><FaMapMarkerAlt /> {eventLocation.city}</span>
                        )}
                    </div>
                    <NavLink to={`/events/${id}`} className={styles.button}>
                        Mehr erfahren →
                    </NavLink>
                </div>
            </div>
            <div className={styles.info}>
                <h3 className={styles.title}>{eventName}</h3>
            </div>
        </div>
    );
};

export default EventCard;
