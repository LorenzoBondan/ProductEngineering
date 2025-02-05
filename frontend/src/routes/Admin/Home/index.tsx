import { Link } from "react-router-dom";

export default function AdminHome() {
    return(
        <main>
            <section className="container">
                <div className="btn btn-primary"><Link to="/admin/users">Usuários</Link></div>
            </section>
        </main>
    );
}