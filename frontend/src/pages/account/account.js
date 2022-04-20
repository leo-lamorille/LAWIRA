import './account.scss';
import {useNavigate} from "react-router-dom";
import {useSelector} from "react-redux";
import {useEffect} from "react";

export default function Account() {
    const navigate = useNavigate();
    const userToken = useSelector(state => state.user.jwt);

    useEffect(() => {
        if (userToken === '') {
            navigate('/account/signIn');
        }
    }, []);

    return <p>Account works !</p>
}