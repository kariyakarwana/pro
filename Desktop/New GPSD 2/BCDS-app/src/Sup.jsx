import React, { useState } from 'react';
import './Sup.css';
import { Helmet } from 'react-helmet';
import profile from "./assets/uim.png";
import pass from "./assets/Lock.png";
import maili from "./assets/Gmail.png";
import wapp from "./assets/WhatsApp.png";
import backgroundImage from './assets/hero-bg.jpg';  // Import the background image

export default function Sup() {
  // Define state variables to hold form data
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [whatsappNumber, setWhatsappNumber] = useState('');
  const [dob, setDob] = useState('');

  // Function to validate email format
  const isValidEmail = (email) => {
    // Basic regex for email validation
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return regex.test(email);
  };

  // Handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();

    // Validate email and password
    if (!isValidEmail(email)) {
      alert('Please enter a valid email address.'); // Alert for invalid email
      return;
    }
    
    if (password.length < 8) {
      alert('Password must be more than 7 characters long.'); // Alert for password length
      return;
    }

    try {
      console.log('Sending data:', { email, password, whatsappNumber, dob }); // Log the data being sent
      // Make a POST request to the back-end API
      const response = await fetch('http://localhost:5038/signup', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, password, whatsappNumber, dob }), // Send form data in request body
      });

      const data = await response.json();
      if (response.ok) {
        // Display success message
        alert('Sign Up Success: ' + data.msg);
        console.log('Sign Up Success:', data.msg);
      } else {
        // Display error message
        alert(data.msg || 'Sign Up Failed'); // Alert for error message
        console.error('Sign Up Error:', data.msg);
      }
    } catch (error) {
      alert('Sign Up Failed'); // Alert for network errors
      console.error('Sign Up Failed:', error);
    }
  };

  return (
    <>
      <Helmet>
        <meta charSet="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
      </Helmet>
      <div className="sin-main" style={{ backgroundImage: `url(${backgroundImage})` }}>
        <div className="sub-main">
          <div>
            <div className="imgs-i">
              <div className="container-images">
                <img src={profile} alt="profile" className="profile" />
              </div>
            </div>
            <div>
              <h2 className="sii">Sign Up</h2>
              {/* Sign Up Form */}
              <form onSubmit={handleSubmit}>
                <div className="input-f">
                  <img src={maili} alt="Email" className="email" />
                  <input
                    type="email"
                    placeholder="Email"
                    className="name"
                    required
                    value={email}
                    onChange={(e) => setEmail(e.target.value)} // Update state on input change
                  />
                </div>
                <div className='input-f'>
                  <img src={pass} alt="Password" className="pass" />
                  <input
                    type="password"
                    placeholder="Password"
                    className="name"
                    required
                    value={password}
                    onChange={(e) => setPassword(e.target.value)} // Update state on input change
                  />
                </div>
                <div className='input-f'>
                  <img src={wapp} alt="DOB" className="pass" />
                  <input
                    type="date"
                    placeholder="Date of birth"
                    className="name"
                    required
                    value={dob}
                    onChange={(e) => setDob(e.target.value)} // Update state on input change
                  />
                </div>
                <div className='input-f'>
                  <img src={wapp} alt="Whatsapp" className="pass" />
                  <input
                    type="text"
                    placeholder="Whatsapp Number"
                    className="name"
                    required
                    value={whatsappNumber}
                    onChange={(e) => setWhatsappNumber(e.target.value)} // Update state on input change
                  />
                </div>
                <div className="login-button">
                  <button type="submit">Sign Up</button> {/* Submit button */}
                </div>
              </form>
              {/* Messages will now be displayed via alerts */}
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
