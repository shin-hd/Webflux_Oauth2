import { CircularProgress, Paper } from "@mui/material";
import { CardsState } from "modules/cards";
import { Card } from "lib/api/cards";

const colors = ["#bcaaa4", "#f5f5f5", "#fff176", "#4aedc4", "#5393ff"];

const CardList = ({ cards }: { cards?: Card[] }) => {
  return (
    <div className="flex space-x-5">
      {cards?.map((card, index) => (
        <Paper
          key={index}
          className="grid w-24 h-24 p-2 font-bold"
          elevation={3}
          sx={{
            background: colors[card.rank - 1],
          }}
        >
          <span className="grid">No.{card.no}</span>
          <div className="grid text-sm text-center items-center rounded-md bg-opacity-20 bg-white">
            <span>{card.name}</span>
          </div>
        </Paper>
      ))}
    </div>
  );
};

const PickResult = ({ cardList }: CardsState) => {
  return (
    <div className="flex flex-col w-[608px] h-[264px]  space-y-6 p-6 shadow-inner shadow-violet-200 bg-purple-50 items-start justify-start">
      {cardList.loading ? (
        <div className="flex h-full self-center items-center">
          <CircularProgress color="secondary" />
        </div>
      ) : (
        <>
          <CardList cards={cardList?.data?.slice(0, 5)} />
          <CardList cards={cardList?.data?.slice(5, 10)} />
        </>
      )}
    </div>
  );
};

export default PickResult;
