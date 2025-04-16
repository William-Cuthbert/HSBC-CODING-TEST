import React, { useState } from 'react';

function App() {
  const [letter, setLetter] = useState('');
  const [count, setCount] = useState(null);

  const handleCheck = async () => {
    if (letter.length !== 1) {
      alert("Enter exactly one letter");
      return;
    }

    const res = await fetch(`http://localhost:8080/api/cities/count?letter=${letter}`);
    const data = await res.json();
    setCount(data.count);
  };

  return (
    <div style={{ padding: "2rem", fontFamily: "Arial" }}>
      <h1>City Counter</h1>
      <input value={letter} onChange={(e) => setLetter(e.target.value)} maxLength={1} />
      <button onClick={handleCheck}>Check</button>
      {count !== null && <h2>Number of cities: {count}</h2>}
    </div>
  );
}

export default App;
