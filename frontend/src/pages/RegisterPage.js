import React, { useState } from 'react';
import API from '../api/axiosConfig';
import { useNavigate } from 'react-router-dom';

export default function RegisterPage() {
  const [form, setForm] = useState({ name:'', email:'', password:'', role:'BUYER' });
  const [msg, setMsg] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await API.post('/auth/register', form);
      setMsg('Registered successfully! Please login.');
      setTimeout(() => navigate('/login'), 2000);
    } catch (err) {
      setMsg('Registration failed. Email may already exist.');
    }
  };

  return (
    <div className="row justify-content-center">
      <div className="col-md-5">
        <div className="card shadow p-4">
          <h3 className="text-center mb-4">Register</h3>
          {msg && <div className="alert alert-info">{msg}</div>}
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label>Name</label>
              <input className="form-control" value={form.name}
                onChange={e => setForm({...form, name: e.target.value})} required />
            </div>
            <div className="mb-3">
              <label>Email</label>
              <input type="email" className="form-control" value={form.email}
                onChange={e => setForm({...form, email: e.target.value})} required />
            </div>
            <div className="mb-3">
              <label>Password</label>
              <input type="password" className="form-control" value={form.password}
                onChange={e => setForm({...form, password: e.target.value})} required />
            </div>
            <div className="mb-3">
              <label>Register As</label>
              <select className="form-select" value={form.role}
                onChange={e => setForm({...form, role: e.target.value})}>
                <option value="BUYER">Buyer</option>
                <option value="VENDOR">Vendor</option>
              </select>
            </div>
            <button type="submit" className="btn btn-success w-100">Register</button>
          </form>
        </div>
      </div>
    </div>
  );
}