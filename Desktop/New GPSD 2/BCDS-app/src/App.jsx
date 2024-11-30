import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'; // Use Routes instead of Switch
import Suwanari from './suwanari';
import Sup from './Sup';
import Sin from './Sin';

const App = () => {
  return (
    <Router>
      <div className="App">
        <Routes> {/* Change Switch to Routes */}
          <Route path="/" element={<Sin />} /> {/* Change component prop to element */}
          <Route path="/sup" element={<Sup />} />
          <Route path="/suwanari" element={<Suwanari />} />
          {/* Add other routes as needed */}
        </Routes>
      </div>
    </Router>
  );
};

export default App;
