import React, { useState, useEffect } from 'react';
import Logo from './assets/Sri-Lankas-Health-Ministry.png';
import './Suwanari.css';
import Card from './components/Card';
import "slick-carousel/slick/slick.css"; 
import "slick-carousel/slick/slick-theme.css";
import ImageGallery from './components/ImageGallery';
import nurses from "./assets/nurses.jpg";
import FaceBook from "./assets/Facebook-Logo.jpg";
import Youtube from "./assets/Youtube.jpg";


const Suwanari = () => {
  //handle cards
  const handleCardClickFacebook = () => {
    window.open('https://www.facebook.com/WWCSriLanka', '_blank'); // Replace with your Facebook page URL
  };

  const handleCardClickYoutube = () => {
    window.open('https://www.youtube.com/watch?v=lASvPJ3gskE', '_blank'); // Replace with your YouTube video URL
  };
  // State to store the clinic data
  const [clinicData, setClinicData] = useState([]);

  // Fetch clinic data from the backend API
  useEffect(() => {
    // Use your API endpoint to fetch clinic data
    fetch('http://localhost:5038/api/clinic/GetData')
      .then(response => response.json()) // Parse the JSON response
      .then(data => {
        setClinicData(data); // Store the fetched data in the state
      })
      .catch(error => console.error('Error fetching clinic data:', error));
  }, []);

  return (
    <div className="container">
      {/* Header Section */}
      <header className="header">
        <div className="title">
          <h1 className='Suwa'>Suwanari</h1>
          <p>A Project by MOH <br/>Sri Lanka</p>
          <small>
            The Suwanari Project is a health initiative conducted by the Ministry of Health (MOH) in Sri Lanka, aimed at promoting the well-being of women aged 35 and above. This program is a weekly health camp held at all MOH offices every Tuesday. The primary focus is on early detection and prevention of various health conditions through comprehensive health screenings.
          </small>
        </div>
        <div className="logo">
          <img src={Logo} alt="MOH Sri Lanka Logo" />
        </div>
      </header>

      {/*Suwanari Details */}
      <section className='why'>
        <div className='det'>
        <h3>Why Should You Connect with the Suwanari Clinic?</h3>
        <p>As women age, their bodies undergo various hormonal changes that can increase the risk of developing certain health conditions. These changes make women more susceptible to illnesses such as heart disease, high blood pressure, diabetes, and more.
        The Suwanari Clinic provides early detection and preventive care for these conditions, offering timely referrals for treatment when necessary. By connecting with the Suwanari Clinic, women can proactively manage their health and receive the support they need to maintain their well-being.</p>
        </div>
        <div className='imm'>
          <img src={nurses} alt='nurses'></img>
        </div>
      </section>


      {/*Video Section*/ }
      <section className="video-gallery">
        <h3>Video Gallery</h3>
        <div className="video-container">
          {/* Replace these links with your YouTube video URLs */}
          <iframe
            width="560"
            height="315"
            src="https://www.youtube.com/embed/lASvPJ3gskE"
            title="YouTube video 1"
            frameBorder="0"
            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
            allowFullScreen
          ></iframe>
          <iframe
            width="560"
            height="315"
            src="https://www.youtube.com/embed/DZfaEv6eV_E"
            title="YouTube video 2"
            frameBorder="0"
            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
            allowFullScreen
          ></iframe>

        </div>
      </section>
      <section className='main-content'>
        <ImageGallery />
      </section>
      {/* Main Section */}
      <section className="main-content">
        <h3>The key components of the Suwanari Project include:</h3>
        <ul>
          <li><strong>PAP Tests:</strong> To screen for cervical cancer and other potential abnormalities.</li>
          <li><strong>Diabetes Tests:</strong> To check blood sugar levels and diagnose diabetes or pre-diabetes.</li>
          <li><strong>Blood Pressure Tests:</strong> To monitor and manage blood pressure, helping to prevent hypertension and related complications.</li>
          <li><strong>Overall Health Assessments:</strong> Including general check-ups and consultations to address other health concerns and provide relevant medical advice.</li>
        </ul>
        <p>
          By providing these essential health services, the Suwanari Project aims to enhance the quality of life for women over 35, encouraging regular health check-ups and fostering a proactive approach to health management. This initiative reflects the commitment of the MOH Sri Lanka to ensure accessible and preventive healthcare for all women, contributing significantly to the overall public health landscape in the country.

          The project's consistent weekly schedule ensures that women can regularly monitor their health and receive timely interventions, thereby reducing the risk of serious health issues and promoting long-term health and well-being.
        </p>

        {/* Clinic Details Table */}
        <div className="time-table">
          <h3>Clinic Details:</h3>
          <table>
            <thead>
              <tr>
                <th>Date</th>
                <th>Time</th>
                <th>Clinic Name</th>
                <th>Address</th>
              </tr>
            </thead>
            <tbody>
              {clinicData.length > 0 ? (
                clinicData.map((clinic, idx) => (
                  <tr key={idx}>
                    {/* Display date and time dynamically based on your data */}
                    <td>{new Date().toLocaleDateString()}</td>
                    <td>{new Date().toLocaleTimeString()}</td>
                    <td>{clinic.name}</td>
                    <td>{clinic.address}</td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="4">No clinic data available.</td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </section>

      {/* Initiatives Section */}
      <div className="initiatives">
        <div className="Global">
          <Card image={FaceBook} tie="Facebook Page" dis="Connect with us ....." onClick={handleCardClickFacebook}/>
          <Card image={Youtube} tie="Youtube Channel" dis="Connect with us ....." onClick={handleCardClickYoutube}/>
        </div>
      </div>

      {/* Update Section */}
      <footer className="footer">
        <div>
          <hr />
        </div>
        <h3>Want to get updates on Suwanari?</h3>
        <div className="updates">
          <p>Get the latest updates via Notifications</p>
          <div className="toggle-switch">
            {/* Uncomment and implement if using state for subscription */}
            {/* <label className="switch">
              <input type="checkbox" checked={isSubscribed} onChange={handleToggle} />
              <span className="slider"></span>
            </label> */}
          </div>
        </div>
      </footer>
    </div>
  );
};

export default Suwanari;
