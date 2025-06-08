// frontend_app/src/pages/index.jsx
import React, { useState } from 'react';
import { FaSmile, FaBook, FaRocket } from 'react-icons/fa';
import RoomResult from '../common/RoomResult';
import Banner from './Banner/Banner';
import UpcomingEventsSection from './KommendeVeranstalltungen/KommendeVeranstalltungen';

const HomePage = () => {
    const [roomSearchResults, setRoomSearchResults] = useState([]);

    return (
        <div className="home">
            <Banner onSearch={setRoomSearchResults} />

            {roomSearchResults.length > 0 && (
                <div className="content">
                    <RoomResult roomSearchResults={roomSearchResults} />
                </div>
            )}

            <div className="divider">
                <div className="card">
                    <FaSmile className="icon" />
                    <h3>Arbeiten &amp; Spaß</h3>
                    <p>Lass den Alltagsstress hinter dir und entdecke spannende Events.</p>
                </div>
                <div className="card">
                    <FaBook className="icon" />
                    <h3>Lernen</h3>
                    <p>Erweitere dein Wissen mit interessanten Workshops und Kursen.</p>
                </div>
                <div className="card">
                    <FaRocket className="icon" />
                    <h3>Weiterentwickeln</h3>
                    <p>Mach den nächsten Schritt auf deinem Weg zum Erfolg.</p>
                </div>
            </div>

            <UpcomingEventsSection />
        </div>
    );
};

export default HomePage;
