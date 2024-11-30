import React, { useState } from 'react';
import './Sin.css';
import { Link, useNavigate } from 'react-router-dom';
import { Helmet } from 'react-helmet';
import backgroundImage from './assets/hero-bg.jpg';

export default function Sin() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate(); // To navigate to another route

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      // Send a POST request to the backend for sign-in
      const response = await fetch('http://localhost:5038/signin', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, password }),
      });

      const data = await response.json();
      console.log('Response:', data);

      if (response.ok) {
        console.log('Sign In Success:', data.token);
        // Store the JWT token in local storage
        localStorage.setItem('authToken', data.token);

        // Navigate to another route after successful sign-in
        navigate('/suwanari');
        alert('Sign In Successful');
      } else {
        console.error('Sign In Error:', data.msg);
        alert('Sign In Error: ' + data.msg);
      }
    } catch (error) {
      console.error('Sign In Failed:', error);
      alert('Sign In Failed. Please try again.');
    }
  };

  return (
    <>
      <Helmet>
        <meta charSet="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
      </Helmet>
      <div className="sin-main" style={{ backgroundImage: `url(${backgroundImage})` }}>
        <div className='sin-wrapper'>
          <form onSubmit={handleSubmit}>
            <h2>Login</h2>
            <div className="input-f">
              <input
                type="email" // Set type to 'email' for validation
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
              <label>Enter your email</label>
            </div>
            <div className="input-f">
              <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
              <label>Enter your password</label>
            </div>
            <div className="forget">
              <label htmlFor="remember">
                <input type="checkbox" id="remember" />
                <p>Remember me</p>
              </label>
              <Link to="#">Forgot password?</Link>
            </div>
            <button type="submit">Log In</button>
            <div className="register">
              <p>Don&apos t have an account? <Link to="/sup">Sign Up</Link></p>
            </div>
          </form>
        </div>
      </div>
    </>
  );
}
