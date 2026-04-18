import React from 'react';
import { Link } from 'react-router-dom';

export default function HomePage() {
  return (
    <div className="text-center py-5">
      <h1 className="display-4 fw-bold">🛒 VendorHub</h1>
      <p className="lead text-muted">Multi-Vendor E-Commerce Platform with AI-Powered Vendor Ranking</p>
      <div className="mt-4">
        <Link to="/products" className="btn btn-primary btn-lg me-3">Browse Products</Link>
        <Link to="/rankings" className="btn btn-outline-warning btn-lg">View Rankings 🏆</Link>
      </div>
      <div className="row mt-5">
        {[
          { icon:'🏪', title:'For Vendors', text:'Register your store, list products, manage orders.' },
          { icon:'🛍️', title:'For Buyers', text:'Browse products, add to cart, track your orders.' },
          { icon:'🏆', title:'Ranking System', text:'Vendors ranked by rating, sales, fulfillment & response time.' },
          { icon:'⚙️', title:'Admin Control', text:'Approve vendors, ban bad actors, view analytics.' },
        ].map((item, i) => (
          <div key={i} className="col-md-3">
            <div className="card p-3 shadow-sm h-100">
              <h2>{item.icon}</h2>
              <h5>{item.title}</h5>
              <p className="text-muted small">{item.text}</p>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}