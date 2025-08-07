import React, { useEffect, useState } from 'react';

export default function App() {
  const [accounts, setAccounts] = useState([]);

  useEffect(() => {
    fetch('/api/accounts')
      .then(res => res.json())
      .then(setAccounts)
      .catch(console.error);
  }, []);

  return (
    <div style={{ maxWidth: '600px', margin: '0 auto', padding: '2rem', fontFamily: 'Arial, sans-serif' }}>
      <h1>E-Wallet Accounts</h1>
      <ul>
        {accounts.map(acc => (
          <li key={acc.id}>
            {acc.name} - {acc.wallet ? acc.wallet.balance : 0}
          </li>
        ))}
      </ul>
    </div>
  );
}
