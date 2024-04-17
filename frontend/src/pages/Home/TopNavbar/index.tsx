import { Link } from 'react-router-dom';
import './styles.css';

const TopNavbar = () => {
    return(
        <nav className='topnavbar-container'>
            <span className="not-registered">Do you have already an account?</span>
            <Link to="/auth/login" className="login-link-register">
                SIGN IN
            </Link>
            <span className="not-registered">Don't have an account?</span>
            <Link to="/auth/signup" className="login-link-register">
                REGISTER NOW
            </Link>
        </nav>
    );
}

export default TopNavbar;