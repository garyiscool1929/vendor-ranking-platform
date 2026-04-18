import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function Navbar() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => { logout(); navigate('/login'); };

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark px-4">
      <Link className="navbar-brand fw-bold" to="/">🛒 VendorHub</Link>
      <div className="collapse navbar-collapse">
        <ul className="navbar-nav me-auto">
          <li className="nav-item"><Link className="nav-link" to="/products">Products</Link></li>
          <li className="nav-item"><Link className="nav-link" to="/rankings">🏆 Rankings</Link></li>
          <li className="nav-item"><Link className="nav-link" to="/cart">Cart</Link></li>
          {user?.role === 'ROLE_VENDOR' && <li className="nav-item"><Link className="nav-link" to="/vendor">Dashboard</Link></li>}
          {user?.role === 'ROLE_ADMIN' && <li className="nav-item"><Link className="nav-link" to="/admin">Admin</Link></li>}
        </ul>
        <div className="d-flex">
          {user ? (
            <>
              <span className="navbar-text text-light me-3">👋 {user.name}</span>
              <button className="btn btn-outline-light btn-sm" onClick={handleLogout}>Logout</button>
            </>
          ) : (
            <>
              <Link className="btn btn-outline-light btn-sm me-2" to="/login">Login</Link>
              <Link className="btn btn-light btn-sm" to="/register">Register</Link>
            </>
          )}
        </div>
      </div>
    </nav>
  );
}