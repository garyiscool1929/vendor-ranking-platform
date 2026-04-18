import React, { useEffect, useState } from 'react';
import API from '../api/axiosConfig';

export default function AdminPanel() {
  const [vendors, setVendors] = useState([]);

  useEffect(() => {
    API.get('/vendors').then(res => setVendors(res.data));
  }, []);

  const updateStatus = async (id, action) => {
    await API.put(`/vendors/${id}/${action}`);
    setVendors(vendors.map(v => v.id === id ? { ...v, status: action === 'approve' ? 'APPROVED' : 'BANNED' } : v));
  };

  const recalculate = async () => {
    await API.post('/rankings/recalculate');
    alert('Rankings recalculated!');
  };

  return (
    <div>
      <h2>⚙️ Admin Panel</h2>
      <button className="btn btn-warning mb-3" onClick={recalculate}>♻️ Recalculate Rankings</button>
      <table className="table table-bordered">
        <thead className="table-dark">
          <tr><th>Store Name</th><th>Status</th><th>Score</th><th>Actions</th></tr>
        </thead>
        <tbody>
          {vendors.map(v => (
            <tr key={v.id}>
              <td>{v.storeName}</td>
              <td>
                <span className={`badge ${v.status === 'APPROVED' ? 'bg-success' : v.status === 'BANNED' ? 'bg-danger' : 'bg-warning'}`}>
                  {v.status}
                </span>
              </td>
              <td>{v.score?.toFixed(2)}</td>
              <td>
                <button className="btn btn-sm btn-success me-2" onClick={() => updateStatus(v.id, 'approve')}>Approve</button>
                <button className="btn btn-sm btn-danger" onClick={() => updateStatus(v.id, 'ban')}>Ban</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}