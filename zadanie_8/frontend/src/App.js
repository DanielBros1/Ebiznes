import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './components/Login';
import AuthSuccess from './pages/AuthSuccess';

function App() {
  return (
      <Router>
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/auth-success" element={<AuthSuccess />} />
        </Routes>
      </Router>
  );
}

export default App;
