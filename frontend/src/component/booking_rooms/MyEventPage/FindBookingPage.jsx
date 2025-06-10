import React, { useState } from 'react';
import ApiService from '../../../service/ApiService';
import './FindBookingPage.css';

const FindBookingPage = () => {
    const [confirmationCode, setConfirmationCode] = useState('');
    const [bookingDetails, setBookingDetails] = useState(null);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);

    const handleSearch = async () => {
        if (!confirmationCode.trim()) {
            setError("Please enter a booking confirmation code");
            setBookingDetails(null);
            setTimeout(() => setError(''), 5000);
            return;
        }
        try {
            setLoading(true);
            const response = await ApiService.getBookingByConfirmationCode(confirmationCode);
            setBookingDetails(response.booking);
            setError(null);
        } catch (err) {
            setError(err.response?.data?.message || err.message);
            setBookingDetails(null);
            setTimeout(() => setError(''), 5000);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div>

            <div className="titel-block">
                <h2 className="fbp-title">Find Booking</h2>
            </div>
        <div className="find-booking-page">


            <div className="fbp-card">
                <div className="fbp-search">
                    <input
                        type="text"
                        placeholder="Enter your booking confirmation code"
                        value={confirmationCode}
                        onChange={e => setConfirmationCode(e.target.value)}
                        disabled={loading}
                    />
                    <button onClick={handleSearch} disabled={loading}>
                        {loading ? 'Loading…' : 'Find'}
                    </button>
                </div>

                {error && <p className="fbp-error">{error}</p>}

                {bookingDetails && (
                    <div className="fbp-result-card">
                        {/* Etkinlik Adı */}
                        <h3>{bookingDetails.eventName}</h3>

                        {/* Rezervasyon Meta */}
                        <p><strong>Confirmation Code:</strong> {bookingDetails.bookingConfirmationCode}</p>
                        <p>
                            <strong>Date:</strong>{' '}
                            {new Date(bookingDetails.eventDate).toLocaleDateString('de-DE', {
                                day: '2-digit', month: '2-digit', year: 'numeric'
                            })}
                        </p>
                        <p><strong>Location:</strong> {bookingDetails.eventLocation.address}</p>
                        <p><strong>Tickets:</strong> {bookingDetails.numberOfTickets}</p>

                        <hr />

                        {/* Kullanıcı Bilgileri */}
                        <h4>Booker Details</h4>
                        <p><strong>Name:</strong> {bookingDetails.user.name}</p>
                        <p><strong>Email:</strong> {bookingDetails.user.email}</p>

                        {/* İsterseniz telefon da ekleyebilirsiniz */}
                        {bookingDetails.user.phoneNumber && (
                            <p><strong>Phone:</strong> {bookingDetails.user.phoneNumber}</p>
                        )}
                    </div>
                )}
            </div>
        </div>
        </div>
    );
};

export default FindBookingPage;
