import { Route, Routes, Link } from 'react-router-dom';
import '../components/navi.css';  // Make sure the path is correct
import Suwanari from '../suwanari';  // Ensure the component path is correct

export default function NaveBox() {
  return (
    <div>
      <div className="naveBox">
        <div className="logo">
          {/* <img src={log} alt="Logo" /> */}
        </div>
        <div className="tabs">
          {/*<Link to="/">HOME</Link>
          <Link to="/awareness">Awareness</Link>
          <Link to="/whatotdo">Self Assessment</Link>*/}
          <div className="suwanari">
            <Link to="/Suwanari">Suwanari</Link>
          </div>
          {/*<div className='sin'>
            <Link to="/sin">Sign In</Link>
          </div>*/}
        </div>
      </div>
      <Routes>
        {/* <Route path="/" element={} />
        <Route path="/awareness" element={} />
        <Route path="/whatotdo" element={} /> */}
        <Route path="/Suwanari" element={<Suwanari />} />
        {/* <Route path="/sin" element={} />
        <Route path="/sup" element={} /> */}
      </Routes>
    </div>
  );
}
