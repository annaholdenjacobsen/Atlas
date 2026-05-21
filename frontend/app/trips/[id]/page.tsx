interface TripPageProps {
  params: Promise<{ id: string }>;
}

export default async function TripPage({ params }: TripPageProps) {
  const { id } = await params;

  return (
    <div>
      <h1>Trip {id}</h1>
    </div>
  );
}
