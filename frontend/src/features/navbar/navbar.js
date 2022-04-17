import customTheme from './customTheme';
import { makeStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import IconButton from '@material-ui/core/IconButton';
import MenuItem from '@material-ui/core/MenuItem';
import Menu from '@material-ui/core/Menu';
import AccountCircle from '@material-ui/icons/AccountCircle';
import LocalGroceryStoreOutlinedIcon from '@mui/icons-material/LocalGroceryStoreOutlined';
import LogoutOutlinedIcon from '@mui/icons-material/LogoutOutlined';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import MenuIcon from '@material-ui/icons/Menu';
import {useState} from "react";
import {ThemeProvider} from "@material-ui/core";
import {Link} from "react-router-dom";

const useStyles = makeStyles((theme) => ({
    grow: {
        flexGrow: 1,
        backgroundColor: '#262626'
    },
    menuButton: {
        marginRight: theme.spacing(2),
    },
    title: {
        display: 'none',
        textDecoration: 'none',
        color: 'white',
        paddingRight: '2vw',
        fontWeight: 'bold',
        [theme.breakpoints.up('sm')]: {
            display: 'block',
        },
    },
    mobileTitle: {
        textDecoration: 'none',
        fontWeight: 'bold',
        color: '#000000',
        [theme.breakpoints.up('sm')]: {
            display: 'block',
        },
    },
    sectionDesktop: {
        display: 'none',
        [theme.breakpoints.up('md')]: {
            display: 'flex',
        },
    },
    sectionMobile: {
        display: 'flex',
        [theme.breakpoints.up('md')]: {
            display: 'none',
        },
    },
    iconButton: {
        textDecoration: 'none',
        color: '#ffffff',
    }
}));

export default function Navbar() {
    const classes = useStyles();
    const [mobileMoreAnchorEl, setMobileMoreAnchorEl] = useState(null);

    const isMobileMenuOpen = Boolean(mobileMoreAnchorEl);

    const handleMobileMenuClose = () => {
        setMobileMoreAnchorEl(null);
    };

    const handleMenuClose = () => {
        handleMobileMenuClose();
    };

    const handleMobileMenuOpen = (event) => {
        setMobileMoreAnchorEl(event.currentTarget);
    };

    const mobileMenuId = 'primary-search-account-menu-mobile';
    const renderMobileMenu = (
        <Menu
            anchorEl={mobileMoreAnchorEl}
            anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
            id={mobileMenuId}
            keepMounted
            transformOrigin={{ vertical: 'top', horizontal: 'right' }}
            open={isMobileMenuOpen}
            onClose={handleMobileMenuClose}
        >
            <MenuItem onClick={handleMenuClose}>
                <Link to="/aboutus" className={classes.mobileTitle}>
                    A propos
                </Link>
            </MenuItem>
            <MenuItem onClick={handleMenuClose}>
                <Link to="/product" className={classes.mobileTitle}>
                    Produit
                </Link>
            </MenuItem>
        </Menu>
    );

    return (
        <ThemeProvider theme={customTheme}>
            <AppBar position="static">
                <Toolbar>
                    <div className={classes.sectionMobile}>
                        <IconButton
                            edge="start"
                            aria-label="account"
                            aria-controls={mobileMenuId}
                            aria-haspopup="true"
                            className={classes.iconButton}
                            onClick={handleMobileMenuOpen}
                        >
                            <MenuIcon/>
                        </IconButton>
                    </div>
                    <div className={classes.sectionDesktop}>
                        <Link to="/home" className={classes.title} variant="h6">
                            LAWIRA
                        </Link>
                        <Link to="/aboutus" className={classes.title}>
                            A propos
                        </Link>
                        <Link to="/product" className={classes.title}>
                            Produit
                        </Link>
                    </div>
                    <div className={classes.grow} />
                    <Link to="admin" className={classes.iconButton}>
                        <IconButton color="inherit">
                            <LockOutlinedIcon />
                        </IconButton>
                    </Link>
                    <Link to="/basket" className={classes.iconButton}>
                        <IconButton color="inherit">
                            <LocalGroceryStoreOutlinedIcon />
                        </IconButton>
                    </Link>
                    <Link to="/account" className={classes.iconButton}>
                        <IconButton color="inherit">
                            <AccountCircle />
                        </IconButton>
                    </Link>
                    <IconButton
                        color="inherit"
                    >
                        <LogoutOutlinedIcon />
                    </IconButton>
                </Toolbar>
            </AppBar>
            {renderMobileMenu}
        </ThemeProvider>
    )
}