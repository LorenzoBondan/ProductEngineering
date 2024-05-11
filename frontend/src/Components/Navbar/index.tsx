import { Link, NavLink, useLocation } from 'react-router-dom';
import { getTokenData, hasAnyRoles, isAuthenticated } from 'util/auth';
import { useContext, useEffect, useState } from 'react';
import { AuthContext } from 'AuthContext';
import { removeAuthData } from 'util/storage';
import history from 'util/history';
import minilogo from 'assets/images/todeschini-heart.png';
import {ReactComponent as TodeschiniLogo} from 'assets/images/todeschini-logo.svg';
import struct from 'assets/images/struct.png';
import item from 'assets/images/item.png';
import hammer from 'assets/images/hammer.png';
import trash from 'assets/images/recycle.png';
import profileIcon from 'assets/images/profile.png';
import adminIcon from 'assets/images/admin.png';
import logoutIcon from 'assets/images/logout.png';
import printIcon from 'assets/images/print.png';
import './styles.css';

const Navbar = () => {
    const location = useLocation();

    const { authContextData, setAuthContextData } = useContext(AuthContext);

    useEffect(() => {
        if(isAuthenticated()){
          setAuthContextData({
            authenticated: true,
            tokenData: getTokenData()
          })
        }
        else{
          setAuthContextData({
            authenticated: false,
          })
        }
      }, [setAuthContextData]);

      const handleLogoutClick = (event : React.MouseEvent<HTMLAnchorElement>) => {
        event.preventDefault(); 
        
        removeAuthData(); 
    
        setAuthContextData({
          authenticated: false,
        })
    
        history.replace('/'); 
    }

    const [isExpanded, setExpendState] = useState(false);

    return(
        <nav className= {isExpanded ? 'admin-nav-container' : 'admin-nav-container-expanded'}>
            <div className={isExpanded ? 'navbar-title' : 'navbar-title-expanded'} >
                <Link to="/">
                    {isExpanded ? <TodeschiniLogo/> : <img src={minilogo} alt="logo" />}
                </Link>
            </div>
            <div className='hambuger-container'>
                <button	className="hamburger" onClick={() => setExpendState(!isExpanded)}>
                    <span></span>
                    <span></span>
                    <span></span>
                </button>
            </div>
            <ul className='ul-container'>
                {isAuthenticated() && (
                <>
                <li>
                    <NavLink to="/homestructs" className={isExpanded ? "admin-nav-item " : "admin-nav-item-expanded " + (location.pathname === "/homestructs" ? "active-nav-item" : "")}>
                        <img src={struct} alt="" />
                        { isExpanded && <p>Estruturas</p>}
                    </NavLink>
                </li>
                <li>
                    <NavLink to="/homeitems" className={isExpanded ? "admin-nav-item " : "admin-nav-item-expanded " + (location.pathname === "/homeitems" ? "active-nav-item" : "")}>
                        <img src={item} alt="" />
                        {isExpanded && <p>Itens</p>}
                    </NavLink>
                </li>
                <li>
                    <NavLink to="/homebasematerials" className={isExpanded ? "admin-nav-item " : "admin-nav-item-expanded " + (location.pathname === "/homebasematerials" ? "active-nav-item" : "")}>
                        <img src={hammer} alt="" />
                        {isExpanded && <p>Materiais base</p>}
                    </NavLink>
                </li>
                { hasAnyRoles(['ROLE_ANALYST', 'ROLE_ADMIN']) && ( 
                    <li>
                        <NavLink to="/trash" className={isExpanded ? "admin-nav-item " : "admin-nav-item-expanded " + (location.pathname === "/trash" ? "active-nav-item" : "")}>
                            <img src={trash} alt="" />
                            {isExpanded && <p>Lixeira</p>}
                        </NavLink>
                    </li>   
                )}
                <li>
                    <NavLink to="/reports" className={isExpanded ? "admin-nav-item " : "admin-nav-item-expanded " + (location.pathname === "/reports" ? "active-nav-item" : "")}>
                        <img src={printIcon} alt="" />
                        {isExpanded && <p>Relatórios</p>}
                    </NavLink>
                </li>
                <li>
                    <NavLink to="/profile" className={isExpanded ? "admin-nav-item " : "admin-nav-item-expanded " + (location.pathname === "/profile" ? "active-nav-item" : "")}>
                        <img src={profileIcon} alt="" />
                        {isExpanded && <p>Perfil</p>}
                    </NavLink>
                </li>
                { hasAnyRoles(['ROLE_ADMIN']) && ( 
                    <li>
                        <NavLink to="/admin" className={isExpanded ? "admin-nav-item " : "admin-nav-item-expanded " + (location.pathname === "/admin" ? "active-nav-item" : "")}>
                            <img src={adminIcon} alt="" />
                            {isExpanded && <p>Admin</p>}
                        </NavLink>
                    </li>   
                )}
                <li>
                    <NavLink to="/" className={isExpanded ? "login-nav-item" : "login-nav-item-expanded"} onClick={handleLogoutClick}>
                        <img src={logoutIcon} alt="" />
                        {isExpanded && <p>Logout</p>}
                    </NavLink>
                </li>
                </>
                )}
            </ul>
        </nav>
    );
}

export default Navbar;