import React from 'react';

export default function ProductCard({ product }) {
  const addToCart = () => {
    const cart = JSON.parse(localStorage.getItem('cart') || '[]');
    const existing = cart.find(i => i.id === product.id);
    if (existing) existing.qty += 1;
    else cart.push({ ...product, qty: 1 });
    localStorage.setItem('cart', JSON.stringify(cart));
    alert(`${product.name} added to cart!`);
  };

  return (
    <div className="col-md-3 mb-4">
      <div className="card h-100 shadow-sm">
        {product.imageUrl && <img src={product.imageUrl} className="card-img-top" alt={product.name} style={{height:'180px', objectFit:'cover'}} />}
        <div className="card-body">
          <h6 className="card-title">{product.name}</h6>
          <p className="text-muted small">{product.category}</p>
          <p className="fw-bold text-success">₹{product.price}</p>
          <p className="text-muted small">Stock: {product.stock}</p>
          <button className="btn btn-sm btn-primary w-100" onClick={addToCart}>Add to Cart</button>
        </div>
      </div>
    </div>
  );
}