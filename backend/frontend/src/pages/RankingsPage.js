import React, { useEffect, useState } from 'react';
import API from '../api/axiosConfig';

export default function RankingsPage() {
  const [rankings, setRankings] = useState([]);

  useEffect(() => {
    API.get('/rankings').then(res => setRankings(res.data));
  }, []);

  const medals = ['🥇','🥈','🥉'];

  return (
    <div>
      <h2>🏆 Vendor Rankings</h2>
      <p className="text-muted">Score = (Avg Rating × 0.4) + (Sales Volume × 0.3) + (Fulfillment Rate × 0.2) + (Response Time × 0.1)</p>
      <table className="table table-bordered table-hover mt-3">
        <thead className="table-dark">
          <tr>
            <th>Rank</th>
            <th>Vendor</th>
            <th>Avg Rating</th>
            <th>Sales</th>
            <th>Fulfillment</th>
            <th>Response Score</th>
            <th>Final Score</th>
          </tr>
        </thead>
        <tbody>
          {rankings.map((r, i) => (
            <tr key={r.id}>
              <td>{medals[i] || `#${r.rankPosition}`}</td>
              <td>{r.vendor?.storeName}</td>
              <td>⭐ {r.avgRating?.toFixed(1)}</td>
              <td>{r.salesVolume}</td>
              <td>{(r.fulfillmentRate * 100).toFixed(1)}%</td>
              <td>{r.responseTimeScore?.toFixed(1)}</td>
              <td><span className="badge bg-success fs-6">{r.finalScore?.toFixed(2)}</span></td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}