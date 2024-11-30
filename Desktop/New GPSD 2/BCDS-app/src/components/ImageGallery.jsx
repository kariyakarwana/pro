import React from 'react';
import Slider from 'react-slick';
import './Gallery.css'; // Optional: for your custom styles
import image1 from '../assets/clinicpic1.jpg';
import image2 from '../assets/clinicpic2.jpg';
import image3 from '../assets/clinicpic4.jpg';
import image4 from '../assets/clinicpic3.jpg';

const ImageGallery = () => {
  const images = [image1,image2,image3,image4];

  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    autoplay: true,
    autoplaySpeed: 2000,
    pauseOnHover: true,
  };

  return (
    <section className="image-gallery">
      <h3>Image Gallery</h3>
      <Slider {...settings}>
        {images.map((image, index) => (
          <div key={index}>
            <img src={image} alt={`Gallery Image ${index + 1}`} style={{ width: '100%', height: 'auto' }} />
          </div>
        ))}
      </Slider>
    </section>
  );
};

export default ImageGallery;
