import React, { useState } from 'react';
import LoginForm from './components/LoginForm.jsx';
import RegisterForm from './components/RegisterForm.jsx';
import Dashboard from './components/Dashboard.jsx';

export default function App() {
  const [user, setUser] = useState(null);
  const [showRegister, setShowRegister] = useState(false);

  if (!user) {
    return (
      <div style={{ maxWidth: '600px', margin: '0 auto', padding: '2rem', fontFamily: 'Arial, sans-serif' }}>
        {showRegister ? (
          <RegisterForm onRegister={() => setShowRegister(false)} />
        ) : (
          <LoginForm onLogin={setUser} />
        )}
        <button onClick={() => setShowRegister((s) => !s)} style={{ marginTop: '1rem' }}>
          {showRegister ? 'Have an account? Login' : 'Need an account? Register'}
        </button>
      </div>
    );
  }

  return <Dashboard user={user} onLogout={() => setUser(null)} />;
}
