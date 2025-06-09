// frontend_app/src/pages/EventDetailsPage.jsx
import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import ApiService from '../../../service/ApiService';
import styles from './EventDetailsPage.module.css';
import { FaCalendarAlt, FaMapMarkerAlt } from 'react-icons/fa';

const EventDetailsPage = () => {
  const { eventId } = useParams();
  const navigate = useNavigate();
  const [event, setEvent] = useState(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const load = async () => {
      setIsLoading(true);
      try {
        const res = await ApiService.getEventById(eventId);
        setEvent(res.event || res);
      } catch (e) {
        setError(e.response?.data?.message || e.message);
      } finally {
        setIsLoading(false);
      }
    };
    load();
  }, [eventId]);

  if (isLoading) return <p className={styles.loading}>Lade Veranstaltungsdetails...</p>;
  if (error) return <p className={styles.error}>{error}</p>;
  if (!event) return <p className={styles.error}>Veranstaltung nicht gefunden.</p>;

  // Some APIs return description under different property
  const {
    eventName,
    eventDescription,
    eventDate,
    eventLocation,
    imageUrl,
    description
  } = event;

  // Determine the actual description text
  const descText = eventDescription || description;

  const formattedDate = new Date(eventDate).toLocaleDateString('de-DE', {
    day: '2-digit',
    month: '2-digit',
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  });

  return (
      <div className={styles.container}>
        <button className={styles.backButton} onClick={() => navigate(-1)}>
          ← Zurück
        </button>

        <div className={styles.imageWrapper}>
          <img src={"data:image/jpeg;base64," + event.eventPhoto} alt={eventName} className={styles.image} />
        </div>

        <div className={styles.content}>
          <h1 className={styles.title}>{eventName}</h1>
          <div className={styles.meta}>
            <span><FaCalendarAlt /> {formattedDate}</span>
            {eventLocation?.city && (
                <span><FaMapMarkerAlt /> {eventLocation.city}</span>
            )}
          </div>

          {/* Display description if available */}
          {descText ? (
              <>
                <h2 className={styles.subTitle}>Beschreibung</h2>
                <p className={styles.description}>{descText}</p>
              </>
          ) : (
              <p className={styles.noDescription}>Keine Beschreibung verfügbar.</p>
          )}
        </div>
      </div>
  );
};

export default EventDetailsPage;
