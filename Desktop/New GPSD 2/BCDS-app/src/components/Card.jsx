import MOL from '../assets/Sri-Lankas-Health-Ministry.png';
import PropTypes from "prop-types";
function Card(props){
    return(
        <div className="card" onClick={props.onClick} style={{ cursor: 'pointer' }}>
            <img className='card-img' src={props.image} alt="Card image"></img>
            <h2 className='card-title'>{props.tie}</h2>
            <p className='card-text'>{props.dis}</p>
        </div>
    )
}

Card.propTypes = {
        tie: PropTypes.String,
        dis: PropTypes.String,
        image: PropTypes.string.isRequired, // Add image prop
        onClick: PropTypes.func.isRequired,
}
Card.defaultProps = {
    tie: "Title",
    dis: "Discription",
    image: {MOL},
}

export default Card;