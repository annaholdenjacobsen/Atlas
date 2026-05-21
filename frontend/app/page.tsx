import Image from "next/image";
import Link from "next/link";

export default function Home() {
  return (
    <main className="relative flex flex-1 flex-col items-center justify-center">
      {/* Background */}
      <Image
        src="/backgroundHero.jpg"
        alt=""
        fill
        className="object-cover"
        priority
      />

      {/* White card */}
      <div className="relative z-10 flex flex-col items-center gap-6 rounded-2xl bg-white/90 px-12 py-10 shadow-xl backdrop-blur-sm">
        <Image
          src="/fullLogo.png"
          alt="Atlas"
          width={220}
          height={250}
          priority
        />
        <p className="text-zinc-500">Track your travels.</p>
        <div className="flex gap-4">
          <Link
            href="/dashboard"
            className="rounded-md bg-zinc-900 px-4 py-2 text-sm text-white hover:bg-zinc-700"
          >
            Go to dashboard
          </Link>
          <Link
            href="/login"
            className="rounded-md border px-4 py-2 text-sm hover:bg-zinc-50"
          >
            Login
          </Link>
        </div>
      </div>
    </main>
  );
}
