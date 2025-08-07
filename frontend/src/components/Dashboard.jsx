import React, { useEffect, useState } from 'react';

export default function Dashboard({ user, onLogout }) {
  const [accounts, setAccounts] = useState([]);
  const [amount, setAmount] = useState('');
  const [targetId, setTargetId] = useState('');

  useEffect(() => {
    fetch('/api/accounts')
      .then((res) => res.json())
      .then(setAccounts)
      .catch(console.error);
  }, []);

  const handleTransfer = async (e) => {
    e.preventDefault();
    try {
      await fetch('/api/transfer', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ toAccountId: targetId, amount: parseFloat(amount) })
      });
      setAmount('');
      setTargetId('');
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div style={{ maxWidth: '600px', margin: '0 auto', padding: '2rem', fontFamily: 'Arial, sans-serif' }}>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <h1>Welcome {user?.username}</h1>
        <button onClick={onLogout}>Log out</button>
      </div>
      <h2>Your Accounts</h2>
      <ul>
        {accounts.map((acc) => (
          <li key={acc.id}>
            {acc.name} - {acc.wallet ? acc.wallet.balance : 0}
          </li>
        ))}
      </ul>
      <h2>Transfer Funds</h2>
      <form onSubmit={handleTransfer} style={{ display: 'flex', flexDirection: 'column', gap: '0.5rem', maxWidth: '300px' }}>
        <input
          type="text"
          placeholder="Target Account ID"
          value={targetId}
          onChange={(e) => setTargetId(e.target.value)}
          required
        />
        <input
          type="number"
          placeholder="Amount"
          value={amount}
          onChange={(e) => setAmount(e.target.value)}
          required
        />
        <button type="submit">Send</button>
      </form>
    </div>
  );
}
