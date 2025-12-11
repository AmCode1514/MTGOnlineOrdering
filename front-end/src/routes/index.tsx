import { createFileRoute } from "@tanstack/react-router";

export const Route = createFileRoute("/")({
  component: HomeComponent,
});

function HomeComponent() {
  return (
    <main className="min-h-screen bg-background text-foreground">
      <div className="max-w-5xl mx-auto px-6 py-20 space-y-20">
        {/* Hero Section */}
        <section className="text-center space-y-6">
          <h1 className="text-5xl font-bold tracking-tight">
            Welcome to <span className="text-primary">Horizon Games</span>
          </h1>
          <p className="text-xl text-muted">
            Your local home for board games, TCGs, miniatures, and community
            events.
          </p>

          <div className="flex justify-center gap-4 pt-4">
            <button className="px-6 py-3 rounded-lg bg-primary text-white font-medium hover:bg-primary-hover transition">
              Shop Products
            </button>
            <button className="px-6 py-3 rounded-lg border border-foreground/20 font-medium hover:bg-foreground/10 transition">
              View Events
            </button>
          </div>
        </section>

        {/* About Section */}
        <section className="grid md:grid-cols-2 gap-10 items-center">
          <div className="space-y-4">
            <h2 className="text-3xl font-semibold">Who We Are</h2>
            <p className="text-muted">
              Horizon Games is a community-centered tabletop shop offering
              curated board games, trading card games, role-playing books,
              miniatures, and accessories.
            </p>
            <p className="text-muted">
              Whether you're shopping for your next favorite game or joining one
              of our weekly play events, we’re here to make tabletop gaming fun
              and welcoming.
            </p>
          </div>

          <div className="bg-card rounded-xl p-8 shadow-md border border-border">
            <h3 className="text-2xl font-medium mb-4">Store Hours</h3>
            <ul className="space-y-2 text-muted">
              <li>Mon–Thu: 11am – 8pm</li>
              <li>Fri–Sat: 11am – 10pm</li>
              <li>Sunday: 12pm – 6pm</li>
            </ul>
          </div>
        </section>

        {/* Featured Section */}
        <section className="space-y-6">
          <h2 className="text-3xl font-semibold text-center">
            Popular Categories
          </h2>

          <div className="grid sm:grid-cols-2 lg:grid-cols-4 gap-6">
            {[
              "Board Games",
              "Trading Card Games",
              "RPG Books",
              "Miniatures",
            ].map((cat) => (
              <div
                key={cat}
                className="p-6 rounded-xl bg-card border border-border shadow-sm hover:shadow-lg hover:-translate-y-1 transition cursor-pointer"
              >
                <h3 className="text-xl font-semibold">{cat}</h3>
                <p className="text-muted mt-2">
                  Explore our selection of {cat.toLowerCase()}.
                </p>
              </div>
            ))}
          </div>
        </section>
      </div>
    </main>
  );
}
