import { Link, NavLink, useLocation } from 'react-router-dom';
import { useContext, useEffect, useState } from 'react';
import * as authService from '../../services/authService';
import minilogo from '../../assets/images/todeschini-heart.png';
import struct from '../../assets/images/struct.png';
import item from '../../assets/images/item.png';
import hammer from '../../assets/images/hammer.png';
import trash from '../../assets/images/recycle.png';
import profileIcon from '../../assets/images/profile.png';
import adminIcon from '../../assets/images/admin.png';
import logoutIcon from '../../assets/images/logout.png';
import printIcon from '../../assets/images/print.png';
import './styles.css';
import { ContextToken } from '../../utils/context-token';

export default function Navbar() {

    const location = useLocation();

    const { contextTokenPayload, setContextTokenPayload } = useContext(ContextToken);

    function handleLogoutClick() {
        authService.logout();
        setContextTokenPayload(undefined);
    }

    const [isExpanded, setExpendState] = useState(false);

    useEffect(() => console.log(contextTokenPayload && contextTokenPayload.username));

    return (
        <nav className={isExpanded ? 'admin-nav-container' : 'admin-nav-container-expanded'}>
            <div>
                <div className={isExpanded ? 'navbar-title' : 'navbar-title-expanded'}>
                    <Link to="/">
                        <img src={minilogo} alt="logo" />
                    </Link>
                </div>
                <div className='hambuger-container'>
                    <button className="hamburger" onClick={() => setExpendState(!isExpanded)}>
                        <span></span>
                        <span></span>
                        <span></span>
                    </button>
                </div>
                <ul className='ul-container'>
                    {authService.isAuthenticated() && (
                        <>
                            <li>
                                <NavLink to="/homestructs" className={isExpanded ? "admin-nav-item" : "admin-nav-item-expanded " + (location.pathname === "/homestructs" ? "active-nav-item" : "")}>
                                    <img src={struct} alt="" />
                                    {isExpanded && <p>Estruturas</p>}
                                </NavLink>
                            </li>
                            <li>
                                <NavLink to="/homeitems" className={isExpanded ? "admin-nav-item" : "admin-nav-item-expanded " + (location.pathname === "/homeitems" ? "active-nav-item" : "")}>
                                    <img src={item} alt="" />
                                    {isExpanded && <p>Itens</p>}
                                </NavLink>
                            </li>
                            <li>
                                <NavLink to="/homebasematerials" className={isExpanded ? "admin-nav-item" : "admin-nav-item-expanded " + (location.pathname === "/homebasematerials" ? "active-nav-item" : "")}>
                                    <img src={hammer} alt="" />
                                    {isExpanded && <p>Materiais base</p>}
                                </NavLink>
                            </li>
                            {authService.hasAnyRoles(['ROLE_ANALYST', 'ROLE_ADMIN']) && (
                                <li>
                                    <NavLink to="/admin/trash" className={isExpanded ? "admin-nav-item" : "admin-nav-item-expanded " + (location.pathname === "/trash" ? "active-nav-item" : "")}>
                                        <img src={trash} alt="" />
                                        {isExpanded && <p>Lixeira</p>}
                                    </NavLink>
                                </li>
                            )}
                            <li>
                                <NavLink to="/reports" className={isExpanded ? "admin-nav-item" : "admin-nav-item-expanded " + (location.pathname === "/reports" ? "active-nav-item" : "")}>
                                    <img src={printIcon} alt="" />
                                    {isExpanded && <p>Relatórios</p>}
                                </NavLink>
                            </li>
                            <li>
                                <NavLink to="/profile" className={isExpanded ? "admin-nav-item" : "admin-nav-item-expanded " + (location.pathname === "/profile" ? "active-nav-item" : "")}>
                                    <img src={profileIcon} alt="" />
                                    {isExpanded && <p>Perfil</p>}
                                </NavLink>
                            </li>
                            {authService.hasAnyRoles(['ROLE_ADMIN']) && (
                                <li>
                                    <NavLink to="/admin" className={isExpanded ? "admin-nav-item" : "admin-nav-item-expanded " + (location.pathname === "/admin" ? "active-nav-item" : "")}>
                                        <img src={adminIcon} alt="" />
                                        {isExpanded && <p>Admin</p>}
                                    </NavLink>
                                </li>
                            )}
                            {contextTokenPayload && authService.isAuthenticated() ? (
                                <li>
                                    <NavLink to="/" className={isExpanded ? "login-nav-item" : "login-nav-item-expanded"} onClick={handleLogoutClick}>
                                        <img src={logoutIcon} alt="" />
                                        {isExpanded && <p>Logout</p>}
                                    </NavLink>
                                </li>
                            ) : (
                                <Link to="/login">Login</Link>
                            )}
                        </>
                    )}
                </ul>
            </div>
            {contextTokenPayload && authService.isAuthenticated() && (<h4 className={isExpanded ? "navbar-username" : "navbar-username-expanded"}>{contextTokenPayload?.username}</h4>)}
        </nav>
    );    
}