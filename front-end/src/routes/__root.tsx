import { Link, Outlet, createRootRoute } from "@tanstack/react-router";
import { TanStackRouterDevtools } from "@tanstack/react-router-devtools";
import HomeBackground from "../components/HomeBackground";

export const Route = createRootRoute({
  component: RootComponent,
});

function RootComponent() {
  return (
    <>
      <div className="p-4 flex gap-7 justify-center text-xl text-(--color-text)">
        <Link
          to="/"
          activeProps={{
            className: "font-bold",
          }}
          activeOptions={{ exact: true }}
        >
          Home
        </Link>{" "}
        <Link
          to="/about"
          activeProps={{
            className: "font-bold",
          }}
        >
          About
        </Link>
        <Link
          to="/store"
          activeProps={{
            className: "font-bold",
          }}
        >
          Store
        </Link>
        <Link
          to="/contact"
          activeProps={{
            className: "font-bold",
          }}
        >
          Contact
        </Link>
      </div>
      <HomeBackground />
      <Outlet />
      <TanStackRouterDevtools position="bottom-right" />
    </>
  );
}
