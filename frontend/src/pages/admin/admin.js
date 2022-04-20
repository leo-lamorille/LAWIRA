import './admin.scss';
import {useNavigate} from "react-router-dom";
import {useSelector} from "react-redux";
import {useEffect} from "react";

export default function Admin() {
    const navigate = useNavigate();

    const userToken = useSelector(state => state.user.jwt);
    const userRole = useSelector(state => state.user.role);

    useEffect(() => {
        if (userToken === '') {
            navigate('/account/signIn');
        } else if (userRole !== 'ADMIN') {
            navigate('/home');
        }

    }, []);

    return <p>Admin works !</p>
}