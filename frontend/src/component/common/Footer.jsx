// frontend_app/src/components/Footer.jsx
import React from "react";

const FooterComponent = () => {
    const mainLinks = [
        { href: "/", label: "Startseite" },
        { href: "/events", label: "Veranstaltungskalender" },
        { href: "/service", label: "Bürgerservice" },
        { href: "/magazin", label: "Stadtmagazin" },
        { href: "/social", label: "Soziale Medien" },
        { href: "/unternehmen", label: "Tochtergesellschaften" },
    ];

    const legalLinks = [
        { href: "/impressum", label: "Impressum" },
        { href: "/barrierefreiheit", label: "Barrierefreiheitserklärung" },
        { href: "/datenschutz", label: "Datenschutzerklärung" },
        { href: "/kontakt", label: "Kontakt" },
        { href: "/presse", label: "Presse" },
        { href: "/stellen", label: "Stellenangebote" },
        { href: "/newsletter", label: "Newsletter" },
    ];

    const socialIcons = [
        { href: "https://facebook.com/stadtherne", src: "/assets/icons/facebook.png", alt: "Facebook" },
        { href: "https://instagram.com/stadt_herne", src: "/assets/icons/instagram.png", alt: "Instagram" },
        { href: "https://linkedin.com/company/stadt-herne", src: "/assets/icons/linkedin.png", alt: "LinkedIn" },
        // … weitere Icons
    ];

    return (
        <footer className="footer">
            <div className="footer-top-spacer" role="presentation" />

            <div className="footer-content fullwidth">
                <div className="footer-logo">
                    <a href="/">
                        <img
                            src="/assets/images/hernelogo.png"
                            alt="Logo Stadt Herne"
                        />
                    </a>
                </div>
                <div className="container">
                    {/* Logo & Kontakt */}
                    <div className="footer-box">
                        <address className="footer-address">
                            Friedrich-Ebert-Platz 2<br />
                            44623 Herne<br />
                            Telefon: 02323 / 16 - 0<br />
                            Fax: 02323 / 16 - 2100<br />
                            E-Mail:{" "}
                            <a href="mailto:info@herne.de">info@herne.de</a><br />
                            Web: <a href="https://www.herne.de">www.herne.de</a>
                        </address>
                    </div>

                    {/* Haupt-Links */}
                    <div className="footer-box">
                        <ul className="footer-links">
                            {mainLinks.map(link => (
                                <li key={link.href}>
                                    <a href={link.href}>{link.label}</a>
                                </li>
                            ))}
                        </ul>
                    </div>

                    {/* Social Icons */}
                    <div className="footer-box">
                        <div className="footer-social">
                            {socialIcons.map(icon => (
                                <a
                                    key={icon.src}
                                    href={icon.href}
                                    target="_blank"
                                    rel="noopener noreferrer"
                                >
                                    <img src={icon.src} alt={icon.alt} />
                                </a>
                            ))}
                        </div>
                    </div>

                    {/* Rechtliches */}
                    <div className="footer-box">
                        <ul className="footer-legal">
                            {legalLinks.map(link => (
                                <li key={link.href}>
                                    <a href={link.href}>{link.label}</a>
                                </li>
                            ))}
                        </ul>
                    </div>
                </div>
            </div>

            {/* Copyright & Back to Top */}
            <div className="footer-bottom fullwidth">
                <div className="container footer-bottom-inner">
                    <div />
                    <div className="footer-copy">
                        © Stadt Herne 2025
                    </div>
                    <div className="footer-toplink">
                        <a href="#seitenanfang" title="Zum Seitenanfang">
                            ↑
                        </a>
                    </div>
                </div>
            </div>
        </footer>
    );
};

export default FooterComponent;

